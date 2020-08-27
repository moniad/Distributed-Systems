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

public interface IRoomController extends IDevice
{
    Room setParameter(String roomId, String parameterType, short parameterValue, com.zeroc.Ice.Current current)
        throws ImproperRoomParameterValue,
               UnknownDevicePowerState,
               UnknownRoomId,
               UnknownRoomParameterType;

    /** @hidden */
    static final String[] _iceIds =
    {
        "::Ice::Object",
        "::SmartHome::IDevice",
        "::SmartHome::IRoomController"
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
        return "::SmartHome::IRoomController";
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
     * @throws com.zeroc.Ice.UserException -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_setParameter(IRoomController obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_roomId;
        String iceP_parameterType;
        short iceP_parameterValue;
        iceP_roomId = istr.readString();
        iceP_parameterType = istr.readString();
        iceP_parameterValue = istr.readShort();
        inS.endReadParams();
        Room ret = obj.setParameter(iceP_roomId, iceP_parameterType, iceP_parameterValue, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        Room.ice_write(ostr, ret);
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
        "setDevicePowerState",
        "setParameter"
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
                return IDevice._iceD_setDevicePowerState(this, in, current);
            }
            case 7:
            {
                return _iceD_setParameter(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
