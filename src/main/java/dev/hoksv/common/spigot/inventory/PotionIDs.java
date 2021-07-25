package dev.hoksv.common.spigot.inventory;


public enum PotionIDs {
    REGENERATION(8193),
    SPEED(8194),
    FIRE_RESISTANCE(8195),
    POISON(8196),
    HEAL(8197),
    NIGHT_VISION(8198),
    WEAKNESS(8200),
    INCREASE_DAMAGE(8201),
    SLOW(8202),
    JUMP(8203),
    HARM(8204),
    WATER_BREATHING(8205),
    INVISIBILITY(8206),
    ;

    int data;

    PotionIDs(int data) {
        this.data = data;
    }

    public short getDataShort() {
        return (short) data;
    }


    public byte getDataByte() {
        return (byte) data;
    }
}
