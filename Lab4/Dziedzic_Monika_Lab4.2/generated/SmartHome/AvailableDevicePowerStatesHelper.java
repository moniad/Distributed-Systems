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

/**
 * Helper class for marshaling/unmarshaling AvailableDevicePowerStates.
 **/
public final class AvailableDevicePowerStatesHelper
{
    public static void write(com.zeroc.Ice.OutputStream ostr, DevicePowerState[] v)
    {
        if(v == null)
        {
            ostr.writeSize(0);
        }
        else
        {
            ostr.writeSize(v.length);
            for(int i0 = 0; i0 < v.length; i0++)
            {
                DevicePowerState.ice_write(ostr, v[i0]);
            }
        }
    }

    public static DevicePowerState[] read(com.zeroc.Ice.InputStream istr)
    {
        final DevicePowerState[] v;
        final int len0 = istr.readAndCheckSeqSize(1);
        v = new DevicePowerState[len0];
        for(int i0 = 0; i0 < len0; i0++)
        {
            v[i0] = DevicePowerState.ice_read(istr);
        }
        return v;
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<DevicePowerState[]> v)
    {
        if(v != null && v.isPresent())
        {
            write(ostr, tag, v.get());
        }
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, DevicePowerState[] v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            AvailableDevicePowerStatesHelper.write(ostr, v);
            ostr.endSize(pos);
        }
    }

    public static java.util.Optional<DevicePowerState[]> read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            DevicePowerState[] v;
            v = AvailableDevicePowerStatesHelper.read(istr);
            return java.util.Optional.of(v);
        }
        else
        {
            return java.util.Optional.empty();
        }
    }
}
