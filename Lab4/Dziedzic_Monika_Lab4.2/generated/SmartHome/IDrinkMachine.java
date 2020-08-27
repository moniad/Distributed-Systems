//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.3
//
// <auto-generated>
//
// Generated from file `smarthome.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package SmartHome;

public interface IDrinkMachine extends IDevice
{
    Tea makeTea(String drinkType, String teaType, short temperature, short sugarLevel, com.zeroc.Ice.Current current)
        throws ImproperSugarLevelValue,
               ImproperTemperatureValue,
               UnknownDevicePowerState,
               UnknownDrinkType,
               UnknownTeaType;

    Chocolate makeChocolate(String drinkType, String chocolateType, short temperature, com.zeroc.Ice.Current current)
        throws ImproperTemperatureValue,
               UnknownChocolateType,
               UnknownDevicePowerState,
               UnknownDrinkType;

    /** @hidden */
    static final String[] _iceIds =
    {
        "::Ice::Object",
        "::SmartHome::IDevice",
        "::SmartHome::IDrinkMachine"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::SmartHome::IDrinkMachine";
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
     * @throws com.zeroc.Ice.UserException -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_makeTea(IDrinkMachine obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_drinkType;
        String iceP_teaType;
        short iceP_temperature;
        short iceP_sugarLevel;
        iceP_drinkType = istr.readString();
        iceP_teaType = istr.readString();
        iceP_temperature = istr.readShort();
        iceP_sugarLevel = istr.readShort();
        inS.endReadParams();
        Tea ret = obj.makeTea(iceP_drinkType, iceP_teaType, iceP_temperature, iceP_sugarLevel, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        Tea.ice_write(ostr, ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
     * @throws com.zeroc.Ice.UserException -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_makeChocolate(IDrinkMachine obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_drinkType;
        String iceP_chocolateType;
        short iceP_temperature;
        iceP_drinkType = istr.readString();
        iceP_chocolateType = istr.readString();
        iceP_temperature = istr.readShort();
        inS.endReadParams();
        Chocolate ret = obj.makeChocolate(iceP_drinkType, iceP_chocolateType, iceP_temperature, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        Chocolate.ice_write(ostr, ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /** @hidden */
    final static String[] _iceOps =
    {
        "getAvailableCommands",
        "getCurrentDeviceParameters",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "makeChocolate",
        "makeTea",
        "setDevicePowerState"
    };

    /** @hidden */
    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return IDevice._iceD_getAvailableCommands(this, in, current);
            }
            case 1:
            {
                return IDevice._iceD_getCurrentDeviceParameters(this, in, current);
            }
            case 2:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 3:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 4:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 5:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 6:
            {
                return _iceD_makeChocolate(this, in, current);
            }
            case 7:
            {
                return _iceD_makeTea(this, in, current);
            }
            case 8:
            {
                return IDevice._iceD_setDevicePowerState(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
