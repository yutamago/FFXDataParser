package script.model;

import java.util.ArrayList;
import java.util.List;

public class ScriptFunc extends ScriptField {
    public List<ScriptField> inputs;
    public int funcspace;

    public ScriptFunc(String name, String type, String internalName, boolean brackets) {
        super(name, type, internalName);
        this.inputs = brackets ? new ArrayList<>() : null;
    }

    public ScriptFunc(String name, String type, String internalName, List<ScriptField> inputs) {
        super(name, type, internalName);
        this.inputs = inputs;
    }

    public ScriptFunc(String name, String type, String internalName, ScriptField i1) {
        super(name, type, internalName);
        this.inputs = List.of(i1);
    }

    public ScriptFunc(String name, String type, String internalName, ScriptField i1, ScriptField i2) {
        super(name, type, internalName);
        this.inputs = List.of(i1, i2);
    }

    public ScriptFunc(String name, String type, String internalName, ScriptField i1, ScriptField i2, ScriptField i3) {
        super(name, type, internalName);
        this.inputs = List.of(i1, i2, i3);
    }

    public ScriptFunc(String name, String type, String internalName, ScriptField i1, ScriptField i2, ScriptField i3, ScriptField i4) {
        super(name, type, internalName);
        this.inputs = List.of(i1, i2, i3, i4);
    }

    public ScriptFunc(String name, String type, String internalName, ScriptField i1, ScriptField i2, ScriptField i3, ScriptField i4, ScriptField i5) {
        super(name, type, internalName);
        this.inputs = List.of(i1, i2, i3, i4, i5);
    }

    public ScriptFunc(String name, String type, String internalName, ScriptField i1, ScriptField i2, ScriptField i3, ScriptField i4, ScriptField i5, ScriptField i6) {
        super(name, type, internalName);
        this.inputs = List.of(i1, i2, i3, i4, i5, i6);
    }

    public ScriptFunc(String name, String type, String internalName, ScriptField i1, ScriptField i2, ScriptField i3, ScriptField i4, ScriptField i5, ScriptField i6, ScriptField i7) {
        super(name, type, internalName);
        this.inputs = List.of(i1, i2, i3, i4, i5, i6, i7);
    }

    public ScriptFunc(String name, String type, String internalName, ScriptField i1, ScriptField i2, ScriptField i3, ScriptField i4, ScriptField i5, ScriptField i6, ScriptField i7, ScriptField i8) {
        super(name, type, internalName);
        this.inputs = List.of(i1, i2, i3, i4, i5, i6, i7, i8);
    }

    public ScriptFunc(String name, String type, String internalName, ScriptField i1, ScriptField i2, ScriptField i3, ScriptField i4, ScriptField i5, ScriptField i6, ScriptField i7, ScriptField i8, ScriptField i9) {
        super(name, type, internalName);
        this.inputs = List.of(i1, i2, i3, i4, i5, i6, i7, i8, i9);
    }

    public String getType(List<StackObject> params) {
        return type;
    }

    @Override
    public String toString() {
        String groupStr = idx != null ? ScriptConstants.FUNCSPACES[idx / 0x1000] + '.' : "";
        if (isNameless()) {
            return groupStr + getHexIndex();
        }
        return groupStr + super.toString();
    }

    public String callB5(List<StackObject> params) {
        int len = params.size();
        if (len != (inputs == null ? 0 : inputs.size())) {
            return "ERROR, func " + this + " called with " + len + " params but needs " + (inputs == null ? 0 : inputs.size()) + "!";
        }
        StringBuilder str = new StringBuilder();
        str.append(this);
        if (inputs == null) {
            return str.toString();
        }
        str.append('(');
        if (len == 0) {
            return str.toString() + ')';
        }
        for (int i = 0; i < len; i++) {
            StackObject obj = params.get(i);
            String paramType = inputs.get(i).type;
            StackObject typed = obj.expression || "unknown".equals(paramType) ? obj : new StackObject(paramType, obj);
            str.append(inputs.get(i).name).append('=').append(typed).append(", ");
        }
        return str.substring(0, str.length() - 2) + ')';
    }

    public String callD8(List<StackObject> params) {
        return "call " + callB5(params);
    }

    protected boolean isNameless() {
        return (name == null || name.isEmpty()) && (internalName == null || internalName.isEmpty());
    }
}
