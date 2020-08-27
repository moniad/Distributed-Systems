package sr.ice.model.machines;

import SmartHome.*;
import com.zeroc.Ice.Current;

import java.util.*;

public class ChocolateMachine extends ChocolateMachineController implements IChocolateMachine {

    private final DeviceType type = DeviceType.DRINKMACHINE;
    private final String serialNumber = UUID.randomUUID().toString();
    private final String name;
    private DevicePowerState devicePowerState;
    private short maxTemperature;

    private List<ChocolateType> chocolateTypes;

    public ChocolateMachine(String name) {
        this.name = name;
        this.devicePowerState = DevicePowerState.OFF;
        this.maxTemperature = 100;
        this.chocolateTypes = Arrays.asList(ChocolateType.values());
    }

    @Override
    public Chocolate makeChocolate(String chocolateType, short temperature, Current current) throws ImproperTemperatureValue,
            UnknownDevicePowerState, UnknownChocolateType {

        if (temperature > maxTemperature || temperature < minValue) throw new ImproperTemperatureValue();
        Drink drink = new Drink(temperature);
        setDevicePowerState(DevicePowerState.ON, current);
        try {
            return new Chocolate(drink, ChocolateType.valueOf(chocolateType));
        } catch (IllegalArgumentException e) {
            throw new UnknownChocolateType(); // bad practice, but it was needed
        }
    }

    @Override
    public String getAvailableCommands(Current current) {
        return "CHOCOLATE MACHINE - available commands: " +
                " on, off, makeChocolate(drinkType, chocolateType, temperature), help, params. " +
                "Available chocolate types are: " + Arrays.toString(ChocolateType.values()) +
                ".\nType 'done' to exit device settings";
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
        parameters.put("chocTypes", Arrays.toString(chocolateTypes.toArray()));
        return parameters;
    }

    @Override
    public String setDevicePowerState(DevicePowerState devicePowerState, Current current) throws UnknownDevicePowerState {
        this.devicePowerState = devicePowerState;
        return "Device turned " + devicePowerState.name();
    }
}