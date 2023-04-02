import java.util.ArrayList;
import java.util.List;

public abstract class ScriptFuncLib {
    private static ScriptFunc[] FUNCS;

    private static ScriptFunc putUnknownFunc(int idx, int inputs) {
        List<ScriptField> inputList = new ArrayList<>();
        for (int i = 1; i <= inputs; i++) {
            inputList.add(new ScriptField("p" + i, "unknown"));
        }
        ScriptFunc func = new ScriptFunc("Unknown:" + String.format("%04x", idx), "unknown", inputList);
        FUNCS[idx] = func;
        return func;
    }

    public static ScriptFunc get(int idx, List<StackObject> params) {
        if (FUNCS[idx] == null) {
            System.err.println("Unknown Function call: " + String.format("%04x", idx));
            return null;
        }
        return FUNCS[idx];
    }

    public static void initialize() {
        if (FUNCS == null) {
            FUNCS = new ScriptFunc[0x10000];
            FUNCS[0x0000] = new ScriptFunc("waitFrames", "unknown", new ScriptField("frames", "ae"));
            putUnknownFunc(0x0001, 1);
            putUnknownFunc(0x0002, 3);
            putUnknownFunc(0x0004, 1);
            putUnknownFunc(0x0005, 0);
            putUnknownFunc(0x0006, 3);
            putUnknownFunc(0x0007, 1);
            putUnknownFunc(0x0010, 0);
            putUnknownFunc(0x0011, 2);
            putUnknownFunc(0x0013, 3);
            putUnknownFunc(0x0015, 3);
            putUnknownFunc(0x0016, 1);
            putUnknownFunc(0x0017, 1);
            putUnknownFunc(0x0018, 3);
            putUnknownFunc(0x0019, 3);
            putUnknownFunc(0x001A, 0);
            putUnknownFunc(0x001B, 0);
            putUnknownFunc(0x001C, 2);
            putUnknownFunc(0x001D, 2);
            putUnknownFunc(0x001F, 0);
            putUnknownFunc(0x0020, 0);
            putUnknownFunc(0x0021, 1);
            putUnknownFunc(0x0025, 1);
            putUnknownFunc(0x0028, 1);
            putUnknownFunc(0x0029, 1);
            putUnknownFunc(0x002A, 1);
            putUnknownFunc(0x002B, 1);
            putUnknownFunc(0x002C, 1);
            putUnknownFunc(0x002D, 1);
            putUnknownFunc(0x002E, 1);
            putUnknownFunc(0x002F, 1);
            putUnknownFunc(0x0030, 1);
            putUnknownFunc(0x0033, 1);
            putUnknownFunc(0x0034, 1);
            putUnknownFunc(0x0035, 1);
            putUnknownFunc(0x0036, 1);
            putUnknownFunc(0x0038, 1); // This is speculative
            putUnknownFunc(0x0039, 1); // This is speculative
            putUnknownFunc(0x003A, 1); // This is speculative
            putUnknownFunc(0x003D, 0);
            putUnknownFunc(0x003F, 0);
            putUnknownFunc(0x0042, 1);
            putUnknownFunc(0x0043, 0);
            putUnknownFunc(0x0044, 0);
            putUnknownFunc(0x0046, 0);
            putUnknownFunc(0x004C, 1);
            FUNCS[0x004D] = new ScriptFunc("controllerButtonPressed?", "bool", new ScriptField("button"));
            putUnknownFunc(0x0054, 6);
            putUnknownFunc(0x0055, 1);
            putUnknownFunc(0x0056, 1);
            putUnknownFunc(0x0057, 3);
            putUnknownFunc(0x0058, 2);
            putUnknownFunc(0x0059, 3);
            putUnknownFunc(0x005A, 1);
            putUnknownFunc(0x005C, 1);
            putUnknownFunc(0x005D, 0);
            putUnknownFunc(0x005E, 0);
            putUnknownFunc(0x005F, 0);
            putUnknownFunc(0x0060, 1);
            putUnknownFunc(0x0061, 1);
            putUnknownFunc(0x0062, 1);
            putUnknownFunc(0x0063, 1);
            FUNCS[0x0064] = new ScriptFunc("displayFieldSubtitle?", "unknown", new ScriptField("index?", "ae"), new ScriptField("string", "ae"));
            putUnknownFunc(0x0065, 4);
            putUnknownFunc(0x0066, 2);
            putUnknownFunc(0x0069, 3);
            putUnknownFunc(0x006A, 2);
            putUnknownFunc(0x006B, 1);
            putUnknownFunc(0x006C, 1);
            putUnknownFunc(0x006D, 1);
            putUnknownFunc(0x006E, 1);
            putUnknownFunc(0x006F, 1);
            putUnknownFunc(0x0070, 1);
            putUnknownFunc(0x0071, 1);
            putUnknownFunc(0x0074, 1);
            putUnknownFunc(0x0076, 0);
            putUnknownFunc(0x0077, 1);
            putUnknownFunc(0x0078, 1);
            putUnknownFunc(0x007A, 2);
            putUnknownFunc(0x007C, 1);
            putUnknownFunc(0x007D, 1);
            putUnknownFunc(0x007F, 1);
            putUnknownFunc(0x0080, 0);
            putUnknownFunc(0x0081, 0);
            putUnknownFunc(0x0082, 0);
            putUnknownFunc(0x0083, 0);
            putUnknownFunc(0x0084, 2);
            putUnknownFunc(0x0085, 1);
            putUnknownFunc(0x0086, 0);
            putUnknownFunc(0x0087, 0);
            putUnknownFunc(0x0088, 0);
            putUnknownFunc(0x0089, 0);
            putUnknownFunc(0x008B, 0);
            putUnknownFunc(0x008D, 1);
            putUnknownFunc(0x008F, 1);
            putUnknownFunc(0x0091, 0);
            putUnknownFunc(0x0092, 1);
            putUnknownFunc(0x0094, 1);
            putUnknownFunc(0x0095, 1);
            putUnknownFunc(0x0097, 1);
            putUnknownFunc(0x0098, 2);
            putUnknownFunc(0x009A, 1);
            putUnknownFunc(0x009D, 2);
            putUnknownFunc(0x009E, 4);
            putUnknownFunc(0x00A3, 4);
            putUnknownFunc(0x00A4, 2);
            putUnknownFunc(0x00A5, 2);
            putUnknownFunc(0x00A6, 1);
            putUnknownFunc(0x00A7, 1);
            putUnknownFunc(0x00A8, 1);
            FUNCS[0x00A9] = new ScriptFunc("RandomValue", "ae", false);
            putUnknownFunc(0x00AA, 1);
            putUnknownFunc(0x00AB, 2);
            putUnknownFunc(0x00B1, 1);
            putUnknownFunc(0x00B2, 1);
            putUnknownFunc(0x00B3, 1);
            putUnknownFunc(0x00B4, 1);
            putUnknownFunc(0x00BB, 1);
            putUnknownFunc(0x00BC, 1);
            putUnknownFunc(0x00BE, 1);
            putUnknownFunc(0x00BF, 1); // \
            putUnknownFunc(0x00C0, 1); // -- Technically these aren't certain (zkrn0300)
            putUnknownFunc(0x00C1, 1); // /
            putUnknownFunc(0x00C2, 1);
            putUnknownFunc(0x00C5, 4);
            putUnknownFunc(0x00C6, 0);
            putUnknownFunc(0x00C7, 1);
            putUnknownFunc(0x00C8, 3);
            putUnknownFunc(0x00C9, 1);
            FUNCS[0x00CA] = new ScriptFunc("addPartyMember?", "unknown", new ScriptField("actor"));
            FUNCS[0x00CB] = new ScriptFunc("removePartyMember?", "unknown", new ScriptField("actor"));
            putUnknownFunc(0x00CE, 1);
            putUnknownFunc(0x00CF, 1);
            putUnknownFunc(0x00D0, 0);
            putUnknownFunc(0x00D1, 0);
            putUnknownFunc(0x00D2, 0);
            FUNCS[0x00D5] = new ScriptFunc("playFieldVoiceLine?", "unknown", new ScriptField("voiceFileIndex", "ae"));
            putUnknownFunc(0x00D6, 0);
            putUnknownFunc(0x00D7, 0);
            putUnknownFunc(0x00D8, 0);
            putUnknownFunc(0x00D9, 0);
            putUnknownFunc(0x00DB, 1);
            putUnknownFunc(0x00DC, 0);
            putUnknownFunc(0x00DE, 0);
            putUnknownFunc(0x00DF, 1);
            putUnknownFunc(0x00E0, 1);
            putUnknownFunc(0x00E2, 1);
            putUnknownFunc(0x00E3, 1);
            putUnknownFunc(0x00E4, 1);
            FUNCS[0x00E7] = new ScriptFunc("putPartyMemberInSlot?", "unknown", new ScriptField("slot", "ae"), new ScriptField("actor"));
            putUnknownFunc(0x00E8, 3);
            putUnknownFunc(0x00E9, 1);
            putUnknownFunc(0x00EC, 1);
            putUnknownFunc(0x00ED, 0);
            putUnknownFunc(0x00EE, 1);
            putUnknownFunc(0x00F2, 1);
            putUnknownFunc(0x00F3, 1);
            putUnknownFunc(0x00F4, 3);
            putUnknownFunc(0x00F6, 2);
            putUnknownFunc(0x00F9, 0);
            putUnknownFunc(0x00FA, 1);
            putUnknownFunc(0x00FB, 1);
            putUnknownFunc(0x00FD, 3);
            putUnknownFunc(0x00FE, 2);
            putUnknownFunc(0x0100, 3);
            FUNCS[0x102] = new ScriptFunc("setBackgroundMusic?", "unknown", new ScriptField("index", "ae"));
            putUnknownFunc(0x0103, 1);
            putUnknownFunc(0x0104, 1);
            FUNCS[0x105] = new ScriptFunc("playBackgroundMusic", "unknown", true);
            putUnknownFunc(0x0106, 3);
            putUnknownFunc(0x0108, 5);
            putUnknownFunc(0x0109, 1);
            putUnknownFunc(0x010B, 2);
            putUnknownFunc(0x010C, 2);
            putUnknownFunc(0x010D, 1);
            putUnknownFunc(0x010E, 0);
            putUnknownFunc(0x010F, 4);
            putUnknownFunc(0x0110, 1);
            putUnknownFunc(0x0111, 0);
            putUnknownFunc(0x0112, 0);
            putUnknownFunc(0x0116, 1);
            putUnknownFunc(0x0117, 1);
            putUnknownFunc(0x0119, 1);
            FUNCS[0x0121] = new ScriptFunc("movementStickTilt?", "ae", new ScriptField("axis", "ae"));
            putUnknownFunc(0x0126, 3);
            putUnknownFunc(0x0128, 2);
            putUnknownFunc(0x012B, 1);
            putUnknownFunc(0x0130, 2);
            putUnknownFunc(0x0132, 2);
            putUnknownFunc(0x0133, 2);
            putUnknownFunc(0x0134, 1);
            putUnknownFunc(0x0135, 0);
            putUnknownFunc(0x0136, 1);
            putUnknownFunc(0x0137, 1);
            putUnknownFunc(0x0138, 1);
            putUnknownFunc(0x0139, 8);
            putUnknownFunc(0x013B, 7);
            putUnknownFunc(0x013E, 1);
            putUnknownFunc(0x0141, 3);
            putUnknownFunc(0x0142, 3);
            putUnknownFunc(0x0143, 1);
            putUnknownFunc(0x0144, 1);
            putUnknownFunc(0x0148, 2);
            putUnknownFunc(0x014A, 3);
            putUnknownFunc(0x0151, 1);
            putUnknownFunc(0x0155, 1);
            putUnknownFunc(0x0158, 1);
            FUNCS[0x015B] = new ScriptFunc("obtainTreasure", "unknown", new ScriptField("index?", "ae"), new ScriptField("treasureId", "ae"));
            putUnknownFunc(0x015D, 1);
            putUnknownFunc(0x015E, 1);
            putUnknownFunc(0x015F, 1);
            putUnknownFunc(0x0160, 1);
            putUnknownFunc(0x0161, 1);
            putUnknownFunc(0x016A, 3);
            putUnknownFunc(0x016B, 3);
            putUnknownFunc(0x016D, 1);
            putUnknownFunc(0x016E, 2);
            putUnknownFunc(0x016F, 1);
            FUNCS[0x0171] = new ScriptFunc("restorePartyMemberHP?", "unknown", new ScriptField("actor"));
            FUNCS[0x0172] = new ScriptFunc("restorePartyMemberMP?", "unknown", new ScriptField("actor"));
            putUnknownFunc(0x0177, 1);
            putUnknownFunc(0x017A, 1);
            putUnknownFunc(0x017E, 2);
            putUnknownFunc(0x017F, 1);
            putUnknownFunc(0x0180, 2);
            putUnknownFunc(0x0181, 0);
            putUnknownFunc(0x0184, 1);
            putUnknownFunc(0x0188, 1);
            putUnknownFunc(0x0189, 1);
            putUnknownFunc(0x0192, 1);
            putUnknownFunc(0x0193, 1);
            putUnknownFunc(0x0194, 1);
            putUnknownFunc(0x0195, 0);
            putUnknownFunc(0x0196, 1);
            putUnknownFunc(0x0197, 1);
            putUnknownFunc(0x0198, 3);
            putUnknownFunc(0x019C, 0);
            putUnknownFunc(0x019D, 1);
            putUnknownFunc(0x01A0, 3);
            putUnknownFunc(0x01A7, 1); //Speculation: Silently give takara.bin index?
            putUnknownFunc(0x01AD, 1);
            putUnknownFunc(0x01B0, 2);
            putUnknownFunc(0x01B1, 0);
            putUnknownFunc(0x01B8, 0);
            putUnknownFunc(0x01BA, 2);
            putUnknownFunc(0x01BD, 1);
            putUnknownFunc(0x01C2, 0);
            putUnknownFunc(0x01C3, 1);
            putUnknownFunc(0x01C4, 1);
            putUnknownFunc(0x01C5, 2);
            putUnknownFunc(0x01C9, 2);
            putUnknownFunc(0x01CA, 0);
            putUnknownFunc(0x01CE, 0);
            putUnknownFunc(0x01CF, 0);
            putUnknownFunc(0x01D0, 0);
            putUnknownFunc(0x01D1, 1);
            putUnknownFunc(0x01D4, 2);
            putUnknownFunc(0x01D9, 0);
            putUnknownFunc(0x01DA, 4);
            putUnknownFunc(0x01DC, 0);
            putUnknownFunc(0x01E3, 0);
            putUnknownFunc(0x01E2, 0);
            putUnknownFunc(0x01E7, 2);
            putUnknownFunc(0x01EB, 1);
            putUnknownFunc(0x01EE, 0);
            putUnknownFunc(0x01F0, 1);
            putUnknownFunc(0x01F8, 2);
            putUnknownFunc(0x01F9, 1);
            putUnknownFunc(0x01FA, 0);
            putUnknownFunc(0x01FB, 0);
            putUnknownFunc(0x0200, 1);
            putUnknownFunc(0x0202, 1);
            putUnknownFunc(0x0207, 0);
            putUnknownFunc(0x020D, 1);
            putUnknownFunc(0x0215, 2);
            putUnknownFunc(0x0216, 3);
            putUnknownFunc(0x0217, 0);
            putUnknownFunc(0x021D, 0);
            putUnknownFunc(0x021E, 0);
            putUnknownFunc(0x0220, 1);
            putUnknownFunc(0x0221, 1);
            putUnknownFunc(0x0225, 1);
            putUnknownFunc(0x0226, 1);
            putUnknownFunc(0x022D, 1);
            putUnknownFunc(0x0234, 0);
            putUnknownFunc(0x0235, 0);
            putUnknownFunc(0x0236, 2);
            putUnknownFunc(0x0237, 1); // Inferred based on A7 being a line ender
            putUnknownFunc(0x023A, 1);
            putUnknownFunc(0x023D, 0);
            putUnknownFunc(0x023F, 1);
            putUnknownFunc(0x0241, 1);
            putUnknownFunc(0x0242, 0);
            putUnknownFunc(0x0243, 0);
            putUnknownFunc(0x024A, 1);
            putUnknownFunc(0x0253, 0);
            putUnknownFunc(0x0254, 1);
            putUnknownFunc(0x0256, 1);
            putUnknownFunc(0x0257, 1);
            putUnknownFunc(0x0259, 2);
            putUnknownFunc(0x025B, 1);
            putUnknownFunc(0x025C, 0);
            putUnknownFunc(0x025D, 1);
            putUnknownFunc(0x0262, 0);
            putUnknownFunc(0x0263, 0);
            putUnknownFunc(0x0264, 1);
            putUnknownFunc(0x0266, 0);
            putUnknownFunc(0x0267, 7);
            putUnknownFunc(0x1000, 4);
            putUnknownFunc(0x1001, 1); // This is a guess, could also be 2 if 0x1013 is 0 instead of 1
            putUnknownFunc(0x1002, 1); // This is a guess, could also be 2 if 0x1013 is 0 instead of 1
            putUnknownFunc(0x1005, 2);
            putUnknownFunc(0x1006, 1);
            putUnknownFunc(0x100A, 1);
            putUnknownFunc(0x1013, 1); // This is a guess, could also be 0 if 0x1001 and 0x1002 are 2 instead of 1
            putUnknownFunc(0x4001, 1);
            putUnknownFunc(0x4003, 4);
            putUnknownFunc(0x4004, 1);
            putUnknownFunc(0x4005, 1);
            putUnknownFunc(0x4006, 1);
            putUnknownFunc(0x4007, 1);
            putUnknownFunc(0x4008, 4);
            putUnknownFunc(0x4009, 4);
            putUnknownFunc(0x400A, 1);
            putUnknownFunc(0x400C, 0);
            putUnknownFunc(0x400D, 0);
            putUnknownFunc(0x400E, 1);
            putUnknownFunc(0x400F, 1);
            putUnknownFunc(0x4013, 6);
            putUnknownFunc(0x4014, 1);
            putUnknownFunc(0x4015, 2);
            putUnknownFunc(0x4016, 2);
            putUnknownFunc(0x4017, 2);
            putUnknownFunc(0x4019, 1);
            putUnknownFunc(0x401A, 2);
            putUnknownFunc(0x401B, 1);
            putUnknownFunc(0x401C, 1);
            putUnknownFunc(0x401D, 1);
            putUnknownFunc(0x401F, 0);
            putUnknownFunc(0x4020, 0);
            putUnknownFunc(0x4022, 1);
            putUnknownFunc(0x4023, 1);
            putUnknownFunc(0x4025, 9);
            putUnknownFunc(0x4034, 1);
            putUnknownFunc(0x4036, 3);
            putUnknownFunc(0x4039, 4);
            putUnknownFunc(0x403A, 1);
            putUnknownFunc(0x403B, 2);
            putUnknownFunc(0x4040, 1);
            putUnknownFunc(0x4043, 2);
            putUnknownFunc(0x4044, 3);
            putUnknownFunc(0x5000, 1); // Why was this 0?
            putUnknownFunc(0x5001, 1);
            putUnknownFunc(0x5003, 0);
            putUnknownFunc(0x5005, 0);
            putUnknownFunc(0x5006, 1);
            putUnknownFunc(0x5007, 4);
            putUnknownFunc(0x5008, 1);
            putUnknownFunc(0x5009, 1);
            putUnknownFunc(0x500B, 1);
            putUnknownFunc(0x500D, 1);
            putUnknownFunc(0x500E, 1);
            putUnknownFunc(0x500F, 1);
            putUnknownFunc(0x5010, 2);
            putUnknownFunc(0x5013, 1);
            putUnknownFunc(0x5014, 2);
            putUnknownFunc(0x5015, 4);
            putUnknownFunc(0x5016, 3);
            putUnknownFunc(0x5017, 1);
            putUnknownFunc(0x501A, 5);
            putUnknownFunc(0x501B, 1);
            putUnknownFunc(0x501E, 0);
            putUnknownFunc(0x5020, 0);
            putUnknownFunc(0x5021, 1);
            putUnknownFunc(0x5022, 1);
            putUnknownFunc(0x5025, 2);
            putUnknownFunc(0x5028, 1);
            putUnknownFunc(0x5029, 1);
            putUnknownFunc(0x502A, 1);
            putUnknownFunc(0x502C, 2);
            putUnknownFunc(0x502D, 2);
            putUnknownFunc(0x5032, 0);
            putUnknownFunc(0x5033, 2);
            putUnknownFunc(0x5034, 1);
            putUnknownFunc(0x5036, 5);
            putUnknownFunc(0x5039, 1);
            putUnknownFunc(0x503C, 1);
            putUnknownFunc(0x503D, 1);
            putUnknownFunc(0x503E, 2);
            putUnknownFunc(0x5040, 1);
            putUnknownFunc(0x5042, 5);
            putUnknownFunc(0x5044, 6);
            putUnknownFunc(0x5047, 2);
            putUnknownFunc(0x504C, 2);
            putUnknownFunc(0x504E, 1);
            putUnknownFunc(0x504F, 1);
            putUnknownFunc(0x5051, 0);
            putUnknownFunc(0x5052, 5);
            putUnknownFunc(0x5054, 2);
            putUnknownFunc(0x5056, 1);
            putUnknownFunc(0x5057, 2);
            putUnknownFunc(0x5058, 1);
            putUnknownFunc(0x505C, 1);
            putUnknownFunc(0x505D, 0);
            putUnknownFunc(0x505F, 1);
            putUnknownFunc(0x5061, 2);
            putUnknownFunc(0x5063, 2);
            putUnknownFunc(0x5065, 1);
            putUnknownFunc(0x5066, 1);
            putUnknownFunc(0x5068, 0);
            putUnknownFunc(0x506D, 2);
            putUnknownFunc(0x506E, 1);
            putUnknownFunc(0x506F, 1);
            putUnknownFunc(0x5075, 1);
            putUnknownFunc(0x5078, 2);
            putUnknownFunc(0x5079, 1);
            putUnknownFunc(0x507A, 1);
            putUnknownFunc(0x507B, 3);
            putUnknownFunc(0x507D, 2);
            putUnknownFunc(0x507E, 2);
            putUnknownFunc(0x5081, 1);
            putUnknownFunc(0x5084, 1);
            putUnknownFunc(0x5085, 1);
            putUnknownFunc(0x5087, 0);
            putUnknownFunc(0x5088, 2);
            putUnknownFunc(0x508F, 1);
            putUnknownFunc(0x6000, 1);
            putUnknownFunc(0x6001, 1);
            putUnknownFunc(0x6002, 3);
            putUnknownFunc(0x6003, 3);
            putUnknownFunc(0x6004, 3);
            putUnknownFunc(0x6006, 6);
            putUnknownFunc(0x6007, 6);
            putUnknownFunc(0x6008, 6);
            putUnknownFunc(0x600A, 1);
            putUnknownFunc(0x6010, 1);
            putUnknownFunc(0x6011, 1);
            putUnknownFunc(0x6012, 1);
            putUnknownFunc(0x6014, 4);
            putUnknownFunc(0x6015, 4);
            putUnknownFunc(0x6016, 0);
            putUnknownFunc(0x6017, 4);
            putUnknownFunc(0x6019, 0);
            putUnknownFunc(0x601A, 0);
            putUnknownFunc(0x601D, 4);
            putUnknownFunc(0x601E, 2);
            putUnknownFunc(0x601F, 4);
            putUnknownFunc(0x6020, 3);
            putUnknownFunc(0x6021, 3);
            putUnknownFunc(0x6027, 3);
            putUnknownFunc(0x6028, 5);
            putUnknownFunc(0x6029, 5);
            putUnknownFunc(0x602B, 3);
            putUnknownFunc(0x602D, 0);
            putUnknownFunc(0x602E, 1);
            putUnknownFunc(0x6030, 1);
            putUnknownFunc(0x6032, 4);
            putUnknownFunc(0x6033, 4);
            putUnknownFunc(0x6034, 0);
            putUnknownFunc(0x6035, 4);
            putUnknownFunc(0x6038, 0);
            putUnknownFunc(0x603A, 1);
            putUnknownFunc(0x603B, 1);
            putUnknownFunc(0x603C, 4);
            putUnknownFunc(0x603F, 3);
            putUnknownFunc(0x6040, 6);
            putUnknownFunc(0x6041, 6);
            putUnknownFunc(0x6043, 1);
            putUnknownFunc(0x6044, 6);
            putUnknownFunc(0x6045, 6);
            putUnknownFunc(0x6046, 4);
            putUnknownFunc(0x6047, 4);
            putUnknownFunc(0x6048, 0);
            putUnknownFunc(0x6049, 0);
            putUnknownFunc(0x604A, 0);
            putUnknownFunc(0x604B, 0);
            putUnknownFunc(0x604C, 6);
            putUnknownFunc(0x604D, 6);
            putUnknownFunc(0x604E, 6);
            putUnknownFunc(0x604F, 1);
            putUnknownFunc(0x6050, 5);
            putUnknownFunc(0x6051, 1);
            putUnknownFunc(0x6052, 2);
            putUnknownFunc(0x6059, 2);
            putUnknownFunc(0x605A, 3);
            putUnknownFunc(0x605B, 2);
            putUnknownFunc(0x605C, 5);
            putUnknownFunc(0x605D, 5);
            putUnknownFunc(0x605F, 0);
            putUnknownFunc(0x6060, 0);
            putUnknownFunc(0x6061, 1);
            putUnknownFunc(0x6062, 0);
            putUnknownFunc(0x6063, 0);
            putUnknownFunc(0x6064, 1);
            putUnknownFunc(0x6065, 1);
            putUnknownFunc(0x6069, 5);
            putUnknownFunc(0x606B, 6);
            putUnknownFunc(0x606E, 6);
            putUnknownFunc(0x6071, 6);
            putUnknownFunc(0x6074, 6);
            putUnknownFunc(0x6076, 0);
            putUnknownFunc(0x6077, 0);
            putUnknownFunc(0x6078, 0);
            putUnknownFunc(0x607D, 1);
            putUnknownFunc(0x607E, 1);
            putUnknownFunc(0x607F, 2);
            putUnknownFunc(0x6080, 1);
            putUnknownFunc(0x6085, 3);
            putUnknownFunc(0x6086, 3);
            putUnknownFunc(0x7000, 0);
            putUnknownFunc(0x7001, 1);
            putUnknownFunc(0x7002, 2); //Speculation: Launch battle? (index, transition)
            FUNCS[0x7002] = new ScriptFunc("launchBattle", "unknown", new ScriptField("btlIndex", "ae"), new ScriptField("transition?", "ae"));
            putUnknownFunc(0x7003, 2);
            putUnknownFunc(0x7005, 0);
            putUnknownFunc(0x7006, 2);
            putUnknownFunc(0x7007, 1);
            putUnknownFunc(0x7008, 0);
            putUnknownFunc(0x7009, 1);
            putUnknownFunc(0x700A, 2);
            FUNCS[0x700B] = new ScriptFunc("performMove", "bool", new ScriptField("target", "actor"), new ScriptField("move"));
            putUnknownFunc(0x700C, 8);
            putUnknownFunc(0x700D, 2);
            putUnknownFunc(0x700E, 1);
            FUNCS[0x700F] = new ScriptFuncAccessor("readActorProperty", "actor", null, ScriptConstants.ACTOR_PROPERTIES, "actorProperty");
            FUNCS[0x7010] = new ScriptFunc("defineActorSubset", "actor", new ScriptField("group", "actor"), new ScriptField("property", "actorProperty"), new ScriptField("unknown"), new ScriptField("selector"));
            putUnknownFunc(0x7012, 0);
            FUNCS[0x7014] = new ScriptFunc("chosenMove?", "move", false);
            putUnknownFunc(0x7015, 1);
            putUnknownFunc(0x7016, 1);
            putUnknownFunc(0x7017, 2);
            FUNCS[0x7018] = new ScriptFuncAccessor("writeActorProperty", "actor", "=", ScriptConstants.ACTOR_PROPERTIES, "actorProperty");
            FUNCS[0x7019] = new ScriptFunc("usedMove", "move", false);
            FUNCS[0x701A] = new ScriptFuncAccessor("readMoveProperty", "move", null, ScriptConstants.MOVE_PROPERTIES, "moveProperty");
            FUNCS[0x701B] = new ScriptFunc("overrideAttemptedAction", "unknown", new ScriptField("target", "actor"), new ScriptField("move"));
            putUnknownFunc(0x701C, 1);
            putUnknownFunc(0x701D, 0);
            FUNCS[0x701E] = new ScriptFunc("countActorsIn", "ae", new ScriptField("within", "actor"), new ScriptField("actor"));
            putUnknownFunc(0x701F, 1);
            FUNCS[0x7021] = new ScriptFunc("dereferenceCharacter", "actor", new ScriptField("actor"));
            FUNCS[0x7022] = new ScriptFunc("SetAmbushState", "unknown", new ScriptField("ambushState"));
            putUnknownFunc(0x7023, 1);
            FUNCS[0x7024] = new ScriptFunc("battleVariant", "ad", false);
            FUNCS[0x7025] = new ScriptFunc("defineActorSubset2?", "actor", new ScriptField("group", "actor"), new ScriptField("property", "actorProperty"), new ScriptField("unknown"), new ScriptField("selector"));
            putUnknownFunc(0x7026, 1);
            putUnknownFunc(0x7027, 0);
            FUNCS[0x7028] = new ScriptFunc("scaleOwnSize", "unknown", new ScriptField("x?", "af"), new ScriptField("y?", "af"), new ScriptField("z?", "af"));
            FUNCS[0x7029] = new ScriptFuncAccessor("setSelfFloating", null, "=", new ScriptField("floating", "bool"));
            putUnknownFunc(0x702A, 0);
            putUnknownFunc(0x702B, 0);
            putUnknownFunc(0x702C, 9);
            putUnknownFunc(0x702D, 0);
            putUnknownFunc(0x702E, 1);
            putUnknownFunc(0x702F, 1);
            putUnknownFunc(0x7030, 0);
            putUnknownFunc(0x7031, 0);
            putUnknownFunc(0x7032, 2);
            FUNCS[0x7032] = new ScriptFuncAccessor("setActorFacingAngle", "actor", "=", new ScriptField("facingAngle", "ae"));
            putUnknownFunc(0x7033, 1);
            FUNCS[0x7034] = new ScriptFunc("endBattle", "unknown",new ScriptField("battleEndType"));
            FUNCS[0x7035] = new ScriptFunc("BattleEndType", "battleEndType", false);
            putUnknownFunc(0x7036, 3);
            FUNCS[0x7037] = new ScriptFunc("addMove", "unknown", new ScriptField("actor"), new ScriptField("move"));
            FUNCS[0x7038] = new ScriptFunc("removeMove", "unknown", new ScriptField("actor"), new ScriptField("move"));
            putUnknownFunc(0x7039, 0);
            putUnknownFunc(0x703A, 1);
            FUNCS[0x703B] = new ScriptFunc("setMoveDisabled", "unknown", new ScriptField("actor"), new ScriptField("move"), new ScriptField("disabled", "bool"));
            FUNCS[0x703C] = new ScriptFunc("runBattleScriptA", "unknown", new ScriptField("index", "ae"));
            putUnknownFunc(0x703D, 0);
            putUnknownFunc(0x703E, 0);
            putUnknownFunc(0x703F, 2);
            putUnknownFunc(0x7040, 1);
            putUnknownFunc(0x7041, 0);
            FUNCS[0x7042] = new ScriptFunc("displayTextBox", "unknown", new ScriptField("index?", "ae"), new ScriptField("string", "ae"), new ScriptField("x?", "ae"), new ScriptField("y?", "ae"), new ScriptField("p5", "unknown"));
            FUNCS[0x7043] = new ScriptFunc("closeTextOnConfirm", "unknown", new ScriptField("index", "ae"));
            FUNCS[0x7044] = new ScriptFunc("closeTextImmediately", "unknown", new ScriptField("index", "ae"));
            putUnknownFunc(0x7045, 1);
            putUnknownFunc(0x7046, 1);
            putUnknownFunc(0x7047, 2);
            putUnknownFunc(0x7048, 4);
            putUnknownFunc(0x7049, 4);
            putUnknownFunc(0x704A, 1);
            putUnknownFunc(0x704B, 3);
            putUnknownFunc(0x704C, 1);
            FUNCS[0x7050] = new ScriptFunc("revive/reinitialize", "unknown", new ScriptField("actor"));
            putUnknownFunc(0x7051, 0);
            FUNCS[0x7052] = new ScriptFunc("attachActor", "unknown", new ScriptField( "actor"), new ScriptField( "host","actor"), new ScriptField( "attachmentPoint","ae"));
            putUnknownFunc(0x7053, 9);
            putUnknownFunc(0x7054, 3);
            putUnknownFunc(0x7055, 1);
            putUnknownFunc(0x7056, 1);
            putUnknownFunc(0x7057, 4);
            putUnknownFunc(0x7058, 2);
            putUnknownFunc(0x7059, 1);
            FUNCS[0x705A] = new ScriptFunc("forcePerformMove", "bool", new ScriptField("target", "actor"), new ScriptField("move"));
            putUnknownFunc(0x705B, 1);
            putUnknownFunc(0x705C, 1);
            putUnknownFunc(0x705D, 2);
            putUnknownFunc(0x705F, 1); // cdsp07_01
            putUnknownFunc(0x7061, 0);
            putUnknownFunc(0x7062, 2);
            putUnknownFunc(0x7063, 0);
            FUNCS[0x7064] = new ScriptFunc("playBattleVoiceLine", "unknown", new ScriptField("voiceFileIndex", "ae"));
            putUnknownFunc(0x7065, 0);
            putUnknownFunc(0x7066, 0);
            putUnknownFunc(0x7067, 0);
            putUnknownFunc(0x7068, 0);
            putUnknownFunc(0x7069, 4);
            putUnknownFunc(0x706B, 3);
            putUnknownFunc(0x706D, 2);
            putUnknownFunc(0x706E, 2);
            putUnknownFunc(0x706F, 1);
            putUnknownFunc(0x7070, 0);
            putUnknownFunc(0x7071, 0);
            FUNCS[0x7072] = new ScriptFunc("displayDialogueLine", "unknown", new ScriptField("index?", "ae"), new ScriptField("string", "ae"));
            putUnknownFunc(0x7073, 1);
            putUnknownFunc(0x7074, 1);
            putUnknownFunc(0x7075, 3);
            putUnknownFunc(0x7076, 3);
            putUnknownFunc(0x7077, 2);
            FUNCS[0x7078] = new ScriptFuncAccessor("readMovePropertyForActor", "move", null, ScriptConstants.MOVE_PROPERTIES, "moveProperty", new ScriptField("actor"));
            putUnknownFunc(0x7079, 0);
            putUnknownFunc(0x707A, 1);
            putUnknownFunc(0x707B, 2);
            putUnknownFunc(0x707C, 0);
            putUnknownFunc(0x707D, 2);
            putUnknownFunc(0x707F, 1);
            putUnknownFunc(0x7080, 1);
            putUnknownFunc(0x7081, 0);
            putUnknownFunc(0x7082, 2);
            putUnknownFunc(0x7083, 2);
            putUnknownFunc(0x7084, 0);
            putUnknownFunc(0x7085, 8);
            putUnknownFunc(0x7086, 0);
            putUnknownFunc(0x7087, 3);
            putUnknownFunc(0x7088, 2);
            putUnknownFunc(0x708D, 0);
            putUnknownFunc(0x708E, 0);
            putUnknownFunc(0x708F, 1);
            putUnknownFunc(0x7091, 2);
            putUnknownFunc(0x7092, 1);
            putUnknownFunc(0x7093, 0);
            putUnknownFunc(0x7094, 3);
            putUnknownFunc(0x7095, 0);
            putUnknownFunc(0x7096, 0);
            FUNCS[0x7097] = new ScriptFunc("runBattleScriptB", "unknown", new ScriptField("index", "ae"));
            putUnknownFunc(0x7098, 0);
            putUnknownFunc(0x7099, 1);
            putUnknownFunc(0x709C, 2);
            putUnknownFunc(0x709D, 0);
            putUnknownFunc(0x709E, 3);
            putUnknownFunc(0x709F, 1);
            putUnknownFunc(0x70A0, 1);
            FUNCS[0x70A1] = new ScriptFunc("dereferenceEnemy", "actor", new ScriptField("actor"));
            putUnknownFunc(0x70A2, 1);
            putUnknownFunc(0x70A3, 1);
            putUnknownFunc(0x70A4, 0);
            putUnknownFunc(0x70A5, 3);
            putUnknownFunc(0x70A6, 1);
            putUnknownFunc(0x70A8, 3);
            putUnknownFunc(0x70A9, 1);
            FUNCS[0x70AA] = new ScriptFuncAccessor("readActorProperty", null, null, ScriptConstants.ACTOR_PROPERTIES, "actorProperty");
            FUNCS[0x70AB] = new ScriptFuncAccessor("addActorProperty", null, "+=", ScriptConstants.ACTOR_PROPERTIES, "actorProperty");
            putUnknownFunc(0x70AC, 1);
            putUnknownFunc(0x70AD, 0);
            putUnknownFunc(0x70AE, 0);
            putUnknownFunc(0x70AF, 0);
            putUnknownFunc(0x70B0, 1);
            putUnknownFunc(0x70B1, 1);
            putUnknownFunc(0x70B2, 2);
            putUnknownFunc(0x70B3, 1);
            putUnknownFunc(0x70B4, 0);
            putUnknownFunc(0x70B5, 0);
            putUnknownFunc(0x70B6, 1);
            putUnknownFunc(0x70B7, 2);
            putUnknownFunc(0x70B8, 1);
            putUnknownFunc(0x70BB, 1);
            putUnknownFunc(0x70BC, 1);
            putUnknownFunc(0x70BD, 1);
            putUnknownFunc(0x70BE, 1);
            putUnknownFunc(0x70C0, 1);
            putUnknownFunc(0x70C3, 1);
            putUnknownFunc(0x70C5, 0);
            putUnknownFunc(0x70C9, 2);
            FUNCS[0x70CC] = new ScriptFunc("initializePredefinedGroupTo", "unknown", new ScriptField("actor"));
            FUNCS[0x70CD] = new ScriptFunc("addToPredefinedGroup", "unknown", new ScriptField("actor"));
            FUNCS[0x70CE] = new ScriptFunc("removeFromPredefinedGroup", "unknown", new ScriptField("actor"));
            FUNCS[0x70CF] = new ScriptFunc("voiceLanguage?", "language", false);
            putUnknownFunc(0x70D0, 1);
            putUnknownFunc(0x70D1, 1);
            putUnknownFunc(0x70D3, 3);
            putUnknownFunc(0x70D5, 1);
            putUnknownFunc(0x70D7, 2);
            putUnknownFunc(0x70D8, 1);
            putUnknownFunc(0x70D9, 0);
            putUnknownFunc(0x70DA, 0);
            FUNCS[0x70DC] = new ScriptFunc("changeActorName", "unknown", new ScriptField("actor"), new ScriptField("string", "ae"));
            FUNCS[0x70DE] = new ScriptFunc("SubtitlesEnabled", "bool", false);
            putUnknownFunc(0x70DF, 1);
            FUNCS[0x70E0] = new ScriptFunc("NotCounterattack", "bool", false);
            putUnknownFunc(0x70E2, 2);
            putUnknownFunc(0x70E3, 1);
            putUnknownFunc(0x70E6, 3);
            putUnknownFunc(0x70E7, 1);
            FUNCS[0x70E8] = new ScriptFunc("PlayerTotalGil", "ae", false);
            FUNCS[0x70E9] = new ScriptFunc("YojimboHireAnswer", "ae", false);
            putUnknownFunc(0x70EB, 1); // Could be 0 if EE is 2 but doubtful
            FUNCS[0x70ED] = new ScriptFunc("getItem", "unknown", new ScriptField("item", "move"), new ScriptField("quantity", "ae"));
            putUnknownFunc(0x70EE, 1); // Could be 2 if EB is 0 but doubtful
            putUnknownFunc(0x70EF, 2);
            FUNCS[0x70F1] = new ScriptFunc("clearOwnMoves?", "unknown", true);
            FUNCS[0x70F2] = new ScriptFunc("addMoveToSelf?", "unknown", new ScriptField("move"));
            FUNCS[0x70F3] = new ScriptFunc("RollRandomIn256", "bool", new ScriptField("unknown"), new ScriptField("chance", "ae"));
            putUnknownFunc(0x70F6, 0);
            putUnknownFunc(0x70F7, 1);
            putUnknownFunc(0x70F8, 0);
            putUnknownFunc(0x70FA, 1);
            putUnknownFunc(0x70FB, 5);
            putUnknownFunc(0x70FC, 0);
            putUnknownFunc(0x70FD, 2);
            putUnknownFunc(0x70FE, 4);
            putUnknownFunc(0x70FF, 0);
            putUnknownFunc(0x7100, 1);
            putUnknownFunc(0x7101, 0);
            FUNCS[0x7102] = new ScriptFunc("makeActorHeadFaceActor?", "unknown", new ScriptField("actor"), new ScriptField("target","actor"));
            putUnknownFunc(0x7103, 4);
            putUnknownFunc(0x7104, 3);
            FUNCS[0x7104] = new ScriptFunc("changeMoveAnimation", "unknown", new ScriptField("move"), new ScriptField("anim1", "ae"), new ScriptField("anim2", "ae"));
            putUnknownFunc(0x7105, 0);
            FUNCS[0x7106] = new ScriptFunc("doesActorKnowMove", "bool", new ScriptField("actor"), new ScriptField("move"));
            putUnknownFunc(0x7107, 1);
            putUnknownFunc(0x7108, 2);
            putUnknownFunc(0x7109, 3);
            putUnknownFunc(0x710A, 0);
            putUnknownFunc(0x710B, 0);
            putUnknownFunc(0x710C, 0);
            putUnknownFunc(0x710D, 0);
            putUnknownFunc(0x7110, 1);
            // may be FUNC7111 instead FUNCS[0x7110] = new ScriptFunc("focusCameraOnActor", "unknown", new ScriptField("actor"));
            putUnknownFunc(0x7111, 1);
            putUnknownFunc(0x7112, 4);
            putUnknownFunc(0x7113, 0);
            putUnknownFunc(0x7114, 0);
            putUnknownFunc(0x7115, 0);
            putUnknownFunc(0x7116, 0);
            FUNCS[0x7117] = new ScriptFunc("overrideDeathAnimationWithAction", "unknown", new ScriptField("target", "actor"), new ScriptField("move"));
            putUnknownFunc(0x7118, 1);
            putUnknownFunc(0x7119, 1);
            putUnknownFunc(0x711A, 4);
            putUnknownFunc(0x711C, 1);
            putUnknownFunc(0x711D, 1);
            FUNCS[0x7120] = new ScriptFunc("displayBattleSubtitle?", "unknown", new ScriptField("index?", "ae"), new ScriptField("string", "ae"));
            putUnknownFunc(0x7123, 1);
            putUnknownFunc(0x7124, 1);
            putUnknownFunc(0x7125, 0);
            putUnknownFunc(0x7126, 1);
            putUnknownFunc(0x7127, 2);
            putUnknownFunc(0x8000, 2);
            FUNCS[0x8002] = new ScriptFunc("setGfxActive?", "unknown", new ScriptField("gfxIndex?", "ae"), new ScriptField("active", "bool"));
            putUnknownFunc(0x8003, 1);
            putUnknownFunc(0x8004, 1);
            putUnknownFunc(0x8005, 2);
            putUnknownFunc(0x8006, 1);
            putUnknownFunc(0x8007, 0);
            putUnknownFunc(0x8008, 0);
            putUnknownFunc(0x8009, 2);
            putUnknownFunc(0x800A, 4);
            putUnknownFunc(0x800B, 1);
            putUnknownFunc(0x800C, 1);
            putUnknownFunc(0x800D, 2);
            putUnknownFunc(0x800F, 1);
            putUnknownFunc(0x8010, 1);
            putUnknownFunc(0x8011, 3);
            putUnknownFunc(0x801D, 2);
            putUnknownFunc(0x801E, 1);
            putUnknownFunc(0x801F, 1);
            putUnknownFunc(0x8021, 1);
            putUnknownFunc(0x8022, 2);
            putUnknownFunc(0x8026, 5);
            putUnknownFunc(0x802D, 0);
            putUnknownFunc(0x8030, 2);
            putUnknownFunc(0x8032, 1);
            putUnknownFunc(0x8035, 2);
            putUnknownFunc(0x8036, 2);
            putUnknownFunc(0x8037, 3);
            putUnknownFunc(0x8038, 1);
            putUnknownFunc(0x803A, 1);
            putUnknownFunc(0x803C, 1);
            putUnknownFunc(0x803E, 3);
            putUnknownFunc(0x803F, 1);
            putUnknownFunc(0x8040, 1);
            putUnknownFunc(0x8041, 1);
            putUnknownFunc(0x8045, 3);
            putUnknownFunc(0x804F, 3);
            putUnknownFunc(0x805B, 4);
            putUnknownFunc(0x805E, 1);
            putUnknownFunc(0x805F, 1);
            putUnknownFunc(0x806A, 1);
            putUnknownFunc(0xB000, 2); // Inference based on A2 popping 1 and pushing 1
            putUnknownFunc(0xB001, 0);
            putUnknownFunc(0xB003, 0);
            putUnknownFunc(0xB004, 0);
            putUnknownFunc(0xB009, 0);
            putUnknownFunc(0xB00B, 0);
            putUnknownFunc(0xB00C, 0);
            putUnknownFunc(0xB00D, 0);
            putUnknownFunc(0xC003, 1);
            putUnknownFunc(0xC007, 2);
            putUnknownFunc(0xC009, 1);
            putUnknownFunc(0xC018, 1);
            putUnknownFunc(0xC022, 1);
            putUnknownFunc(0xC02C, 1);
            putUnknownFunc(0xC02F, 2);
            putUnknownFunc(0xC051, 1);
            putUnknownFunc(0xC052, 0);

        }
    }
}
