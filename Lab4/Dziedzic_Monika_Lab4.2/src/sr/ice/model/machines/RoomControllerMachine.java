package sr.ice.model.machines;

import SmartHome.*;
import com.zeroc.Ice.Current;

import java.util.*;

public class RoomControllerMachine extends RoomController implements IRoomController {

    private final DeviceType type = DeviceType.ROOMCONTROLLER;
    private final String serialNumber = UUID.randomUUID().toString();
    private final String name;
    private DevicePowerState devicePowerState;
    private short maxHumidity;
    private short maxLightIntensity;
    private short maxAromaticOil;

    private List<RoomParameterType> roomParameterTypes;
    private List<RoomNameIdentifier> roomNameIdentifiers;

    public RoomControllerMachine(String name) {
        this.name = name;
        this.devicePowerState = DevicePowerState.OFF;
        this.roomParameterTypes = Arrays.asList(RoomParameterType.values());
        this.roomNameIdentifiers = Arrays.asList(RoomNameIdentifier.values());
        this.maxHumidity = 90;
        this.maxLightIntensity = 100;
        this.maxAromaticOil = 5;
    }

    private boolean isValid(RoomParameterType roomParameterType, short parameterValue) {
        if (parameterValue < minValue) return false;
        if (roomParameterType.equals(RoomParameterType.TEMPERATURE) && parameterValue > maxTemperature) {
            return false;
        }
        if (roomParameterType.equals(RoomParameterType.HUMIDITY) && parameterValue > maxHumidity) {
            return false;
        }
        if (roomParameterType.equals(RoomParameterType.LIGHTINTENSITY) && parameterValue > maxLightIntensity) {
            return false;
        }
        return !roomParameterType.equals(RoomParameterType.AROMATICOIL) || parameterValue <= maxAromaticOil;
    }

    @Override
    public Room setParameter(String roomId, String parameterType, short parameterValue, Current current) throws UnknownDevicePowerState,
            ImproperRoomParameterValue, UnknownRoomId, UnknownRoomParameterType {
        RoomParameterType roomParameterType;
        try {
            roomParameterType = RoomParameterType.valueOf(parameterType);
        } catch (IllegalArgumentException e) {
            throw new UnknownRoomParameterType(e);
        }
        if (!isValid(roomParameterType, parameterValue)) throw new ImproperRoomParameterValue();
        setDevicePowerState(DevicePowerState.ON, current);
        try {
            return new Room(RoomNameIdentifier.valueOf(roomId), roomParameterType, parameterValue);
        } catch (IllegalArgumentException e) {
            throw new UnknownRoomId(e); // bad practice, but it was needed
        }
    }

    @Override
    public String getAvailableCommands(Current current) {
        return "ROOM CONTROLLER MACHINE - available commands: " +
                " on, off, setParams(roomId, paramType, paramValue), help, params. " +
                "Available parameters are: " + Arrays.toString(RoomParameterType.values()) +
                " for the following rooms: " + Arrays.toString(RoomNameIdentifier.values()) +
                ".\n Max values for these params are: h - " + maxHumidity + ", light - " + maxLightIntensity + ". aromatic oil - " +
                +maxAromaticOil + ".\nType 'done' to exit device settings";
    }

    @Override
    public Map<String, String> getCurrentDeviceParameters(Current current) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("type", type.toString());
        parameters.put("serialNumber", serialNumber);
        parameters.put("name", name);
        parameters.put("minValues", String.valueOf(minValue));
        parameters.put("devicePowerState", devicePowerState.toString());
        parameters.put("maxTemperature", String.valueOf(maxTemperature));
        parameters.put("maxHumidity", String.valueOf(maxHumidity));
        parameters.put("maxLightIntensity", String.valueOf(maxLightIntensity));
        parameters.put("maxAromaticOil", String.valueOf(maxAromaticOil));
        parameters.put("roomIDs", Arrays.toString(roomNameIdentifiers.toArray()));
        parameters.put("roomParams", Arrays.toString(roomParameterTypes.toArray()));
        return parameters;
    }

    @Override
    public String setDevicePowerState(DevicePowerState devicePowerState, Current current) {
        this.devicePowerState = devicePowerState;
        return "Device turned " + devicePowerState.name();
    }
}
