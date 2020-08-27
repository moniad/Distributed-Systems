
#ifndef SMARTHOME_ICE
#define SMARTHOME_ICE

module SmartHome {
    enum DeviceType { DRINKMACHINE, ROOMCONTROLLER };
    enum RoomNameIdentifier { MUM, DAD, MONIKA, JAREK, KITCHEN, DININGROOM, BATHROOM, BASEMENT };
    enum RoomParameterType { TEMPERATURE, HUMIDITY, LIGHTINTENSITY, AROMATICOIL };
    enum ChocolateType { DARK, WHITE, MILK };
    enum TeaType { WHITE, RED, BLACK, GREEN, SENCHA, OOLONG };
    enum DevicePowerState { ON, OFF };

    struct Room {
        RoomNameIdentifier roomNameId;
        RoomParameterType parameterType;
        short parameterValue;
    };

    struct Drink {
        short temperature;
    };

    struct Chocolate {
        Drink drink;
        ChocolateType chocolateType;
    };

    struct Tea {
        Drink drink;
        TeaType teaType;
        short sugarLevel;
    };

    sequence <string> AllDevicesNames;
    dictionary <string, string> CurrentDeviceParameters;
    sequence <RoomNameIdentifier> AvailableRoomNameIdentifiers;
    sequence <RoomParameterType> AvailableRoomParameterTypes;
    sequence <ChocolateType> AvailableChocolateTypes;
    sequence <TeaType> AvailableTeaTypes;
    sequence <DevicePowerState> AvailableDevicePowerStates;

   class Device {
        DeviceType type;
        string serialNumber;
        string name;
        short minValue = 0;
        DevicePowerState devicePowerState;
   };

   class DrinkController extends Device {
       short maxTemperature = 100;
   };

    class TeaMachineController extends DrinkController{
        AvailableTeaTypes teaTypes;
        short maxSugarLevel;
    };

    class ChocolateMachineController extends DrinkController{
        AvailableChocolateTypes chocolateTypes;
    };

    class RoomController extends Device{
        RoomNameIdentifier roomId;
        short maxTemperature = 25;
        short maxHumidity;
        short maxLightIntensity;
        short maxAromaticOil;
    };

    interface DevicesContainer {
        AllDevicesNames getAllDevicesNames();
    };


// exceptions
    exception UnknownDeviceType {};
    exception UnknownRoomId {};
    exception UnknownRoomParameterType {};
    exception ImproperTemperatureValue {};
    exception ImproperRoomParameterValue {};
    exception ImproperSugarLevelValue {};
    exception UnknownTeaType{};
    exception UnknownChocolateType{};
    exception UnknownDevicePowerState{};


    interface IDevice {
        string getAvailableCommands();
        CurrentDeviceParameters getCurrentDeviceParameters();
        string setDevicePowerState(DevicePowerState devicePowerState) throws UnknownDevicePowerState;
    };

    interface ITeaMachine extends IDevice {
        Tea makeTea(string teaType, short temperature, short sugarLevel) throws UnknownDevicePowerState,
         UnknownTeaType, ImproperTemperatureValue, ImproperSugarLevelValue;
    };

    interface IChocolateMachine extends IDevice {
        Chocolate makeChocolate(string chocolateType, short temperature) throws UnknownDevicePowerState,
         UnknownChocolateType, ImproperTemperatureValue;
    };

    interface IRoomController extends IDevice {
        Room setParameter(string roomId, string parameterType, short parameterValue) throws UnknownDevicePowerState,
        UnknownRoomId, UnknownRoomParameterType, ImproperRoomParameterValue;
    };
};

#endif
