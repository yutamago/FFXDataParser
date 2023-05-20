package script;

import reading.Chunk;
import script.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class ScriptObject {
    private static final boolean WRITE_SCRIPT_PREFIX_BEFORE_JUMPS = false;
    private static final int JUMP_LINE_MINLENGTH = 16;
    private static final int HEX_LINE_MINLENGTH = 48;
    private static final int JUMP_PLUS_HEX_LINE_MINLENGTH = JUMP_LINE_MINLENGTH + HEX_LINE_MINLENGTH + 1;

    protected int[] bytes;
    protected int[] actualScriptCodeBytes;
    protected int absoluteOffset;
    protected int byteCursor = 0;
    protected int[] refFloats;
    protected int[] refInts;
    protected int scriptCodeLength;
    protected int scriptCodeStartAddress;
    protected int scriptCodeEndAddress;
    public StringBuilder jumpTableString = new StringBuilder();
    Stack<StackObject> stack = new Stack<>();
    Map<Integer, String> lastTempTypes = new HashMap<>();
    Map<Integer, String> varTypes = new HashMap<>();
    Map<Integer, List<StackObject>> varEnums = new HashMap<>();
    Map<Integer, StackObject> constants = new HashMap<>();
    String lastCallType = "unknown";
    boolean gatheringInfo = true;
    List<Integer> jumpDestinations = new ArrayList<>();
    Map<Integer, List<String>> reverseJumpDestinations = new HashMap<>();

    int lineCount = 0;
    String textScriptLine;
    String hexScriptLine;
    String jumpLine;
    String warnLine;
    List<String> offsetLines;
    List<String> textScriptLines;
    List<String> hexScriptLines;
    List<String> jumpLines;
    List<String> warnLines;

    public ScriptObject(Chunk chunk) {
        this.bytes = chunk.bytes;
        this.absoluteOffset = chunk.offset;
    }

    public ScriptObject(int[] bytes, int absoluteOffset) {
        this.bytes = bytes;
        this.absoluteOffset = absoluteOffset;
    }

    public void parseScript() {
        byteCursor = 0;
        scriptCodeLength = read4Bytes();
        // System.out.println("Script Length: " + scriptCodeLength);
        int nullAddress2 = read4Bytes();
        int creatorTagAddress = read4Bytes();
        int numberPartAddress = read4Bytes();
        int jumpsEndAddress = read4Bytes();
        skipBytes(0x14);
        int jumpsStartAddress = read4Bytes();
        int weirdRandomFlagsAddress = read4Bytes();
        scriptCodeStartAddress = read4Bytes();
        int numberOfScripts = read2Bytes();
        int numberOfScripts2 = read2Bytes();
        // System.out.println("Number of Scripts: " + numberOfScripts + " / " + numberOfScripts2);
        int[] scriptHeaderOffsets = new int[numberOfScripts];
        for (int i = 0; i < numberOfScripts; i++) {
            scriptHeaderOffsets[i] = read4Bytes();
        }
        int scriptIndex = 0;
        List<ScriptHeader> headers = new ArrayList<>();
        for (int headerStart : scriptHeaderOffsets) {
            byteCursor = headerStart;
            ScriptHeader scriptHeader = parseScriptHeader();
            parseScriptJumps(scriptHeader, scriptIndex);
            scriptIndex++;
            headers.add(scriptHeader);
        }
        parseReferenceFloatsAndInts(headers);
        scriptCodeEndAddress = scriptCodeStartAddress + scriptCodeLength;
        actualScriptCodeBytes = Arrays.copyOfRange(bytes, scriptCodeStartAddress, scriptCodeEndAddress);
        parseScriptCode();
        if (!stack.empty()) {
            System.err.println("Stack not empty: " + stack.size() + " els: " + stack.stream().map(StackObject::toString).collect(Collectors.joining("::")));
        }
        inferEnums();
        gatheringInfo = false;
        parseScriptCode();
    }

    private ScriptHeader parseScriptHeader() {
        ScriptHeader header = new ScriptHeader();
        header.count1 = read2Bytes();
        header.someRefsCount = read2Bytes();
        header.refIntCount = read2Bytes();
        header.refFloatCount = read2Bytes();
        header.entryPointCount = read2Bytes();
        header.maybeJumpCount = read2Bytes();
        header.alwaysZero1 = read4Bytes();
        header.alwaysZero2 = read4Bytes();
        header.someRefsOffset = read4Bytes();
        header.intTableOffset = read4Bytes();
        header.floatTableOffset = read4Bytes();
        header.scriptEntryPointsOffset = read4Bytes();
        header.jumpsOffset = read4Bytes();
        header.alwaysZero3 = read4Bytes();
        header.alwaysZero4 = read4Bytes();
        header.weirdoOffset = read4Bytes();
        return header;
    }

    private void parseReferenceFloatsAndInts(List<ScriptHeader> headers) {
        int lastRefIntTableOffset = -1;
        int lastRefFloatTableOffset = -1;
        for (ScriptHeader h : headers) {
            if (lastRefFloatTableOffset < 0) {
                lastRefFloatTableOffset = h.floatTableOffset;
                byteCursor = h.floatTableOffset;
                refFloats = new int[h.refFloatCount];
                for (int i = 0; i < h.refFloatCount; i++) {
                    refFloats[i] = read4Bytes();
                }
            } else if (h.floatTableOffset != lastRefFloatTableOffset) {
                System.err.println("WARNING, float table mismatch!");
            }
            if (lastRefIntTableOffset < 0) {
                lastRefIntTableOffset = h.intTableOffset;
                byteCursor = h.intTableOffset;
                refInts = new int[h.refIntCount];
                for (int i = 0; i < h.refIntCount; i++) {
                    refInts[i] = read4Bytes();
                }
            } else if (h.intTableOffset != lastRefIntTableOffset) {
                System.err.println("WARNING, int table mismatch!");
            }
        }
    }

    private void parseScriptJumps(ScriptHeader header, int scriptIndex) {
        String sPrefix = "s" + format2Or4Byte(scriptIndex);
        jumpTableString.append(sPrefix).append('\n');
        byteCursor = header.scriptEntryPointsOffset;
        int entryPointCount = header.entryPointCount;
        int[] entryPoints = new int[entryPointCount];
        for (int i = 0; i < entryPointCount; i++) {
            int entryPoint = read4Bytes();
            entryPoints[i] = entryPoint;
            jumpDestinations.add(entryPoint);
            String epSuffix = "e" + format2Or4Byte(i);
            if (!reverseJumpDestinations.containsKey(entryPoint)) {
                reverseJumpDestinations.put(entryPoint, new ArrayList<>());
            }
            String entryPointLabel = sPrefix + epSuffix;
            reverseJumpDestinations.get(entryPoint).add(entryPointLabel);
            jumpTableString.append(epSuffix).append('=').append(String.format("%04x", entryPoint).toUpperCase()).append(' ');
        }
        jumpTableString.append('\n');
        byteCursor = header.jumpsOffset;
        int jumpCount = header.maybeJumpCount;
        int[] jumps = new int[jumpCount];
        for (int i = 0; i < jumpCount; i++) {
            int jump = read4Bytes();
            jumps[i] = jump;
            jumpDestinations.add(jump);
            String jSuffix = "j" + format2Or4Byte(i);
            if (!reverseJumpDestinations.containsKey(jump)) {
                reverseJumpDestinations.put(jump, new ArrayList<>());
            }
            String fullJumpLabel = sPrefix + jSuffix;
            reverseJumpDestinations.get(jump).add(WRITE_SCRIPT_PREFIX_BEFORE_JUMPS ? fullJumpLabel : jSuffix);
            jumpTableString.append(jSuffix).append('=').append(String.format("%04x", jump).toUpperCase()).append(' ');
        }
        jumpTableString.append('\n');
    }

    protected void parseScriptCode() {
        byteCursor = scriptCodeStartAddress;
        lineCount = 0;
        offsetLines = new ArrayList<>();
        hexScriptLines = new ArrayList<>();
        textScriptLines = new ArrayList<>();
        jumpLines = new ArrayList<>();
        warnLines = new ArrayList<>();
        textScriptLine = "";
        hexScriptLine = "";
        jumpLine = "";
        warnLine = "";
        int opcode;
        int nextLineOffset = 0;
        while (byteCursor < scriptCodeEndAddress) {
            opcode = nextAiByte();
            int arg1 = 0;
            int arg2 = 0;
            switch (getArgc(opcode)) {
                case 2:
                    arg1 = nextAiByte();
                    arg2 = nextAiByte();
                case 0:
                default:
                    break;
            }
            process(opcode, arg1, arg2);
            if (getLineEnd(opcode)) {
                if (!stack.empty()) {
                    warnLine += " Stack not empty (" + stack.size() + "): " + stack;
                    stack.clear();
                }
                lineCount++;
                offsetLines.add(String.format("%04x", nextLineOffset).toUpperCase());
                nextLineOffset = byteCursor - scriptCodeStartAddress;
                textScriptLines.add(textScriptLine);
                hexScriptLines.add(hexScriptLine);
                jumpLines.add(jumpLine);
                warnLines.add(warnLine);
                textScriptLine = "";
                hexScriptLine = "";
                jumpLine = "";
                warnLine = "";
            } else {
                hexScriptLine += ' ';
            }
        }
    }

    protected int nextAiByte() {
        int scriptCodeByteCursor = byteCursor - scriptCodeStartAddress;
        if (reverseJumpDestinations.containsKey(scriptCodeByteCursor)) {
            List<String> jumps = reverseJumpDestinations.get(scriptCodeByteCursor);
            String jumpLabels = String.join(",", jumps) + ':';
            jumpLine += jumpLabels;
        }
        int rd = readByte();
        String byteAsString = String.format("%02x", rd).toUpperCase();
        hexScriptLine += byteAsString;
        return rd;
    }

    protected void process(int opcode, int arg1, int arg2) {
        int argv = arg1 + arg2 * 0x100;
        int argvSigned = argv < 0x8000 ? argv : (argv - 0x10000);
        String argvsd = ""+argvSigned;
        String argvsh = format2Or4Byte(argv);
        StackObject p1 = null, p2 = null, p3 = null, p4 = null, p5 = null, p6 = null, p7 = null, p8 = null;
        try {
            switch (getStackPops(opcode)) {
                case 8: p8 = stack.pop();
                case 7: p7 = stack.pop();
                case 6: p6 = stack.pop();
                case 5: p5 = stack.pop();
                case 4: p4 = stack.pop();
                case 3: p3 = stack.pop();
                case 2:
                    p2 = stack.pop();
                case 1:
                    p1 = stack.pop();
                case 0:
                default:
                    break;
            }
        } catch (EmptyStackException e) {
            warnLine += " Empty stack for opcode " + String.format("%02x", opcode).toUpperCase();
            return;
        }
        if (opcode == OpCodes.NOP || opcode == OpCodes.NOP_1D || opcode == OpCodes.NOP_1E || opcode == OpCodes.NOP_76) {
            // nothing
        } else if (opcode >= OpCodes.OR_LOGIC && opcode <= OpCodes.MOD) {
            ScriptField op = ScriptConstants.COMP_OPERATORS.get(opcode);
            String resultType = op.type;
            String p1s = p1.toString();
            String p2s = p2.toString();
            if (opcode == 0x06 || opcode == 0x07) {
                String p1t = resolveType(p1);
                String p2t = resolveType(p2);
                boolean p1w = isWeakType(p1t);
                boolean p2w = isWeakType(p2t);
                if (p1w && !p2w) {
                    p1s = typed(p1, p2t);
                } else if (p2w && !p1w) {
                    p2s = typed(p2, p1t);
                }
            }
            String content = "(" + p1s + " " + op.name + " " + p2s + ")";
            stack.push(new StackObject(resultType, true, content, opcode));
        } else if (opcode == OpCodes.NOT_LOGIC) {
            stack.push(new StackObject("bool", true, "not " + p1, OpCodes.NOT_LOGIC));
        } else if (opcode == OpCodes.NEG) {
            stack.push(new StackObject("unknown", true, "OPUMINUS", OpCodes.NEG));
        } else if (opcode == OpCodes.NOT) {
            stack.push(new StackObject(p1.type, true, "bitNot " + p1, OpCodes.NOT));
        } else if (opcode == OpCodes.GET_DATUM) { // TODO: unhandled in noclip
        } else if (opcode == OpCodes.SET_DATUM_W) { // TODO: unhandled in noclip
        } else if (opcode == OpCodes.SET_DATUM_T) { // TODO: unhandled in noclip
        } else if (opcode == OpCodes.GET_DATUM_INDEX) { // TODO: unhandled in noclip
        } else if (opcode == OpCodes.SET_DATUM_INDEX_W) { // TODO: unhandled in noclip
        } else if (opcode == OpCodes.SET_DATUM_INDEX_T) { // TODO: unhandled in noclip
        } else if (opcode == OpCodes.SET_RETURN_VALUE) {
            lastCallType = p1.type;
            textScriptLine += "(call) " + p1 + ';';
        } else if (opcode == OpCodes.GET_RETURN_VALUE) {
            stack.push(new StackObject(lastCallType, true, "LastCallResult", OpCodes.GET_RETURN_VALUE));
        } else if (opcode == OpCodes.GET_DATUM_DESC) { // TODO: unhandled in noclip
        } else if (opcode == OpCodes.GET_TEST) {
            stack.push(new StackObject("unknown", true, "rX", OpCodes.GET_TEST));
        } else if (opcode == OpCodes.GET_CASE) {
            stack.push(new StackObject("unknown", true, "case", OpCodes.GET_CASE));
        } else if (opcode == OpCodes.SET_TEST) {
            textScriptLine += "Set rX = " + p1;
        } else if (opcode == OpCodes.COPY) {
            stack.push(new StackObject(p1.type, p1));
            stack.push(new StackObject(p1.type, p1));
        } else if (opcode == OpCodes.SET_CASE) {
            textScriptLine += "switch " + p1;
        } else if (opcode == OpCodes.CONST_INT) { // TODO
        } else if (opcode == OpCodes.IMM) { // TODO
        } else if (opcode == OpCodes.CONST_FLOAT) { // TODO
        } else if (opcode == OpCodes.JUMP) { // TODO
        } else if (opcode == OpCodes.BNEZ) { // TODO
        } else if (opcode == OpCodes.BEZ) { // TODO
        } else if (opcode == OpCodes.CALL) { // TODO
        } else if (opcode == OpCodes.RETURN) {
            textScriptLine += "return from subroutine";
        } else if (opcode == OpCodes.FUNC_RET) { // TODO
        } else if (opcode >= OpCodes.SIG_NOACK && opcode <= OpCodes.SIG_ONEND) {
            String type = "queueScript";
            if (opcode == OpCodes.SIG_ONSTART) {
                type += "Sync";
            } else if (opcode == OpCodes.SIG_ONEND) {
                type += "Async";
            }
            String sep = "s" + format2Or4Byte(p2.value) + "e" + format2Or4Byte(p3.value);
            String content = "(" + p1 + ", " + sep + ")";
            stack.push(new StackObject(type, true, type + content, opcode));
        } else if (opcode == OpCodes.SIG_NOACK_SPEC) { // TODO
        } else if (opcode == OpCodes.SIG_1_SPEC) { // TODO
        } else if (opcode == OpCodes.SIG_2_SPEC) { // TODO
        } else if (opcode == OpCodes.END) {
            textScriptLine += "return";
        } else if (opcode == OpCodes.CLEANUP_END) { // TODO
        } else if (opcode == OpCodes.TO_MAIN) { // TODO
        } else if (opcode == OpCodes.CLEANUP_TO_MAIN) {
            textScriptLine += "return (RETTN): " + p1;
        } else if (opcode == OpCodes.DYNAMIC) { // TODO: unhandled in noclip
        } else if (opcode == OpCodes.SIGS_LOW) { // TODO
        } else if (opcode == OpCodes.SIGS_HIGH) { // TODO
        } else if (opcode == OpCodes.CLEANUP_ALL_END) {
            textScriptLine += "direct return?";
        } else if (opcode == OpCodes.SET_JUMP) { // TODO
        } else if (opcode == OpCodes.SET_BNEZ) { // TODO
        } else if (opcode == OpCodes.SET_BEZ) { // TODO
        } else if (opcode == OpCodes.FUNC) { // TODO
        } else if (opcode >= OpCodes.SET_INT && opcode <= 0x5C) {
            lastTempTypes.put(opcode-OpCodes.SET_INT, p1.type);
            textScriptLine += "tempI" + (opcode-OpCodes.SET_INT) + " = " + p1;
        } else if (opcode >= OpCodes.SET_FLOAT && opcode <= 0x66) {
            textScriptLine += "tempF" + (opcode-OpCodes.SET_FLOAT) + " = " + p1;
        } else if (opcode >= OpCodes.GET_INT && opcode <= 0x6A) {
            stack.push(new StackObject(lastTempTypes.getOrDefault(opcode-OpCodes.GET_INT, "unknown"), true, "tempI"+(opcode-OpCodes.GET_INT), opcode));
        } else if (opcode >= OpCodes.GET_FLOAT && opcode <= 0x74) {
            stack.push(new StackObject("float", true, "tempF"+(opcode-OpCodes.GET_FLOAT), opcode));
        } else if (opcode == OpCodes.TEX_UNPACK_IMM) { // TODO
        } else if (opcode == OpCodes.WAIT_DELETE) {
            String sep = "s" + format2Or4Byte(p1.value) + "e" + format2Or4Byte(p2.value);
            textScriptLine += "await " + sep + ';';
        } else if (opcode == OpCodes.WAIT_SPEC_DELETE) { // TODO
        } else if (opcode == OpCodes.EDIT_ENTRY_TABLE) {
            textScriptLine += "REQCHG (" + p1 + ", " + p2 + ", " + p3 + ')';
        } else if (opcode == OpCodes.SET_EDGE_TRIGGER) { // TODO
        } else if (opcode == 0x9F) {
            stack.push(new StackObject("var", true, "var"+argvsh, argv));
        } else if (opcode == 0xA0 || opcode == 0xA1) {
            addVarType(argv, resolveType(p1));
            if (gatheringInfo) {
                if (!varEnums.containsKey(argv)) {
                    varEnums.put(argv, new ArrayList<>());
                }
                varEnums.get(argv).add(p1);
            }
            textScriptLine += "Set ";
            if (opcode == 0xA1) {
                textScriptLine += "(limit) ";
            }
            String val = typed(p1, varTypes.get(argv));
            textScriptLine += "var" + argvsh + " = " + val;
        } else if (opcode == 0xA2) {
            String arrayIndex = '[' + String.format("%04x", p1.value) + ']';
            stack.push(new StackObject("int", true, "var"+argvsh+arrayIndex, argv));
        } else if (opcode == 0xA3 || opcode == 0xA4) {
            String arrayIndex = '[' + String.format("%04x", p1.value) + ']';
            textScriptLine += "Set ";
            if (opcode == 0xA4) {
                textScriptLine += "(limit) ";
            }
            textScriptLine += "var" + argvsh + arrayIndex + " = " + p2;
        } else if (opcode == 0xA7) {
            String arrayIndex = '[' + String.format("%04x", p1.value) + ']';
            stack.push(new StackObject("int", true, "ArrayPointer:var"+argvsh+arrayIndex, argv));
        } else if (opcode == 0xAD) {
            int refInt = refInts[argv];
            String content = "rI[" + argvsh + "]:" + refInt + " [" + String.format("%08x", refInt).toUpperCase() + "h]";
            stack.push(new StackObject("int", false, content, refInt));
        } else if (opcode == 0xAE) {
            stack.push(new StackObject("int", false, argvsd + " [" + argvsh + "h]", argv));
        } else if (opcode == 0xAF) {
            int refFloat = refFloats[argv];
            String content = "rF[" + argvsh + "]:" + Float.intBitsToFloat(refFloat) + " [" + String.format("%08x", refFloat).toUpperCase() + "h]";
            stack.push(new StackObject("float", false, content, refFloat));
        } else if (opcode == 0xB5) {
            processB5(arg1, arg2);
        } else if (opcode == 0xD6) {
            textScriptLine += p1 + " -> j" + argvsh;
        } else if (opcode == 0xD7) {
            textScriptLine += "Check " + p1 + " else jump to j" + argvsh;
        } else if (opcode == 0xD8) {
            processD8(arg1, arg2);
        } else if (opcode == 0xB0) {
            textScriptLine += "Jump to j" + argvsh;
        } else if (opcode == 0xB3) {
            textScriptLine += "Jump to subroutine s" + argvsh;
        }
    }

    protected List<StackObject> popParamsForFunc(int idx) {
        List<StackObject> params = new ArrayList<>();
        try {
            int functionParamCount = getFunctionParamCount(idx);
            switch (functionParamCount) {
                case 9: params.add(0, stack.pop());
                case 8: params.add(0, stack.pop());
                case 7: params.add(0, stack.pop());
                case 6: params.add(0, stack.pop());
                case 5: params.add(0, stack.pop());
                case 4: params.add(0, stack.pop());
                case 3: params.add(0, stack.pop());
                case 2: params.add(0, stack.pop());
                case 1: params.add(0, stack.pop());
                case 0:
                default:
                    break;
            }
        } catch (EmptyStackException e) {
            warnLine += " Empty stack for func " + String.format("%04x", idx);
        }
        return params;
    }

    protected ScriptFunc getAndTypeFuncCall(int idx, List<StackObject> params) {
        ScriptFunc func = ScriptFuncLib.get(idx, params);
        if (func == null) {
            func = new ScriptFunc("Unknown:" + String.format("%04x", idx), "unknown", null, false);
        } else if (func.inputs != null) {
            for (int i = 0; i < func.inputs.size(); i++) {
                typed(params.get(i), func.inputs.get(i).type);
            }
        }
        return func;
    }

    protected void processB5(int opcode, int group) {
        int idx = opcode + group * 0x100;
        List<StackObject> params = popParamsForFunc(idx);
        ScriptFunc func = getAndTypeFuncCall(idx, params);
        stack.push(new StackObject(func.getType(params), true, func.callB5(params), idx));
    }

    protected void processD8(int opcode, int group) {
        int idx = opcode + group * 0x100;
        List<StackObject> params = popParamsForFunc(idx);
        ScriptFunc func = getAndTypeFuncCall(idx, params);
        lastCallType = func.getType(params);
        String call = func.callD8(params);
        textScriptLine += call + ';';
    }

    protected void addVarType(int var, String type) {
        if (!gatheringInfo) {
            return;
        }
        if (!varTypes.containsKey(var)) {
            varTypes.put(var, type);
        } else {
            String prevType = varTypes.get(var);
            if (isWeakType(prevType)) {
                varTypes.put(var, type);
            }
        }
    }

    protected String resolveType(StackObject obj) {
        if (obj == null) {
            return "unknown";
        }
        if ("var".equals(obj.type)) {
            return varTypes.get(obj.value);
        }
        return obj.type;
    }

    protected static boolean isWeakType(String type) {
        return type == null || "int".equals(type) || "unknown".equals(type) || type.isBlank();
    }

    protected String typed(StackObject obj, String type) {
        if (obj == null) {
            return type + ":null";
        } else {
            if ("var".equals(obj.type)) {
                addVarType(obj.value, type);
            }
            if (obj.expression || "unknown".equals(type)) {
                return obj.toString();
            } else {
                return new StackObject(type, obj).toString();
            }
        }
    }

    protected static int getArgc(int opcode) {
        if (opcode == 0xff) {
            return 0;
        } else if (opcode >= 0x80) {
            return 2;
        } else {
            return 0;
        }
    }

    protected int getStackPops(int opcode) {
        int stackpops = ScriptConstants.OPCODE_STACKPOPS[opcode];
        if (stackpops < 0) {
            warnLine += " Undefined stackpops for opcode " + String.format("%02x", opcode);
            return 0;
        }
        return stackpops;
    }

    protected int getFunctionParamCount(int idx) {
        ScriptFunc func = ScriptFuncLib.get(idx, null);
        if (func == null) {
            warnLine += " Undefined stackpops for func " + String.format("%04x", idx);
            return 0;
        }
        return func.inputs != null ? func.inputs.size() : 0;
    }

    protected static boolean getLineEnd(int opcode) {
        return ScriptConstants.OPCODE_ENDLINE.contains(opcode);
    }

    protected void inferEnums() {
        for (Map.Entry<Integer, String> entry : varTypes.entrySet()) {
            Integer varIdx = entry.getKey();
            if (isWeakType(entry.getValue()) && varEnums.containsKey(varIdx)) {
                List<StackObject> enums = varEnums.get(varIdx);
                if (enums.size() == 1 && !enums.get(0).expression) {
                    constants.put(varIdx, enums.get(0));
                } else if (enums.stream().noneMatch(a -> a.expression)) {
                    Set<Integer> distinctContents = enums.stream().map(a -> a.value).collect(Collectors.toSet());
                    if (distinctContents.size() == 2 && distinctContents.contains(0) && (distinctContents.contains(1) || distinctContents.contains(255))) {
                        varTypes.put(varIdx, "bool");
                    }
                }
            }
        }
    }

    private int readByte() {
        int rd = bytes[byteCursor];
        byteCursor++;
        return rd;
    }

    private int read2Bytes() {
        int a = readByte();
        int b = readByte() * 0x100;
        return a + b;
    }

    private int read4Bytes() {
        int a = read2Bytes();
        int b = read2Bytes() * 0x10000;
        return a + b;
    }

    private static String format2Or4Byte(int b) {
        return String.format(b > 0x100 ? "%04x" : "%02x", b).toUpperCase();
    }

    private long read8Bytes() {
        long a = read4Bytes();
        long b = read4Bytes() * 0x100000000L;
        return a + b;
    }

    private void skipBytes(int amount) {
        byteCursor += amount;
    }

    public String allLinesString() {
        StringBuilder al = new StringBuilder("Script code starts at offset ").append(String.format("%04x", scriptCodeStartAddress + absoluteOffset)).append('\n');
        for (int i = 0; i < lineCount; i++) {
            al.append(fullLineString(i));
        }
        return al.toString();
    }

    public String fullLineString(int line) {
        String ol = String.format("%-5s", offsetLines.get(line) + ' ');
        String jl = String.format("%-" + JUMP_LINE_MINLENGTH + "s", jumpLines.get(line)) + ' ';
        String jhl = String.format("%-" + JUMP_PLUS_HEX_LINE_MINLENGTH + "s", jl + hexScriptLines.get(line)) + ' ';
        String tl = textScriptLines.get(line);
        String wl = warnLines.get(line);
        return ol + jhl + tl + wl + '\n';
    }

}
