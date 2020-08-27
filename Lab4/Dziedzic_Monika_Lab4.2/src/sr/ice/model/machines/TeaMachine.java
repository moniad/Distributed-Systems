package sr.ice.model.machines;

import SmartHome.*;
import com.zeroc.Ice.Current;

import java.util.*;

public class TeaMachine extends TeaMachineController implements ITeaMachine {

    private final DeviceType type = DeviceType.DRINKMACHINE;
    private final String serialNumber = UUID.randomUUID().toString();
    private final String name;
    private DevicePowerState devicePowerState;

    private short maxTemperature;
    private short maxSugarLevel;

    private List<TeaType> teaTypes;

    public TeaMachine(String name) {
        this.name = name;
        this.devicePowerState = DevicePowerState.OFF;
        this.maxTemperature = 100;
        this.maxSugarLevel = 10;

        this.teaTypes = Arrays.asList(TeaType.values());
    }

    @Override
    public Tea makeTea(String teaType, short temperature, short sugarLevel, Current current) throws UnknownTeaType,
            UnknownDevicePowerState, ImproperTemperatureValue, ImproperSugarLevelValue {
        setDevicePowerState(DevicePowerState.ON, current);
        if (temperature > maxTemperature || temperature < minValue) throw new ImproperTemperatureValue();
        if (sugarLevel > maxSugarLevel || sugarLevel < minValue) throw new ImproperSugarLevelValue();
        Drink drink = new Drink(temperature);
        try {
            return new Tea(drink, TeaType.valueOf(teaType), sugarLevel);
        } catch (IllegalArgumentException e) {
            throw new UnknownTeaType(); // bad practice, but it was needed
        }
    }

    @Override
    public String getAvailableCommands(Current current) {
        return "TEA MACHINE - available commands: " +
                " on, off, makeTea(drinkType, teaType, temperature, sugarLevel), help, params. " +
                "Available chocolate types are: " + Arrays.toString(TeaType.values()) +
                ".\nMax sugar level is 10. Type 'done' to exit device settings";
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
        parameters.put("maxSugarLevel", String.valueOf(maxSugarLevel));
        parameters.put("teaTypes", Arrays.toString(teaTypes.toArray()));
        return parameters;
    }

    @Override
    public String setDevicePowerState(DevicePowerState devicePowerState, Current current) throws UnknownDevicePowerState {
        this.devicePowerState = devicePowerState;
        return "Device turned " + devicePowerState.name();
    }
}