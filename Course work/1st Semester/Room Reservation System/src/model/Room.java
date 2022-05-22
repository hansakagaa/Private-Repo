package model;

public class Room {
    private String roomId;
    private String roomType;
    private boolean roomMode;

    public Room() {
    }

    public Room(String roomId, String roomType, boolean roomMode) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.roomMode = roomMode;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean isRoomMode() {
        return roomMode;
    }

    public void setRoomMode(boolean roomMode) {
        this.roomMode = roomMode;
    }
}
