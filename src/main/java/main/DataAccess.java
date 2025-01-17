package main;

import model.*;
import script.MonsterFile;

public abstract class DataAccess {
    public static AbilityDataObject[] MOVES = new AbilityDataObject[0x10000];
    public static MonsterFile[] MONSTERS = new MonsterFile[0x1000];
    public static GearAbilityDataObject[] GEAR_ABILITIES;
    public static KeyItemDataObject[] KEY_ITEMS;
    public static TreasureDataObject[] TREASURES;
    public static GearDataObject[] WEAPON_PICKUPS;

    public static AbilityDataObject getMove(int idx) {
        if (MOVES == null) {
            throw new UnsupportedOperationException();
        }
        return MOVES[idx];
    }

    public static GearAbilityDataObject getGearAbility(int idx) {
        if (idx == 0x00FF) {
            return null;
        }
        if (GEAR_ABILITIES == null) {
            throw new UnsupportedOperationException();
        }
        int actual = idx - 0x8000;
        if (actual >= 0 && actual < GEAR_ABILITIES.length) {
            return GEAR_ABILITIES[actual];
        } else {
            return null;
        }
    }

    public static KeyItemDataObject getKeyItem(int idx) {
        if (KEY_ITEMS == null) {
            throw new UnsupportedOperationException();
        }
        int actual = idx - 0xA000;
        if (actual >= 0 && actual < KEY_ITEMS.length) {
            return KEY_ITEMS[actual];
        } else {
            return null;
        }
    }

    public static TreasureDataObject getTreasure(int idx) {
        if (TREASURES == null) {
            throw new UnsupportedOperationException();
        }
        return TREASURES[idx];
    }

    public static MonsterFile getMonster(int idx) {
        if (MONSTERS == null) {
            throw new UnsupportedOperationException();
        }
        int actual = idx - 0x1000;
        if (actual >= 0 && actual < MONSTERS.length) {
            return MONSTERS[actual];
        } else {
            return null;
        }
    }
}
