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

public interface ITeaMachinePrx extends IDevicePrx
{
    default Tea makeTea(String teaType, short temperature, short sugarLevel)
        throws ImproperSugarLevelValue,
               ImproperTemperatureValue,
               UnknownDevicePowerState,
               UnknownTeaType
    {
        return makeTea(teaType, temperature, sugarLevel, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default Tea makeTea(String teaType, short temperature, short sugarLevel, java.util.Map<String, String> context)
        throws ImproperSugarLevelValue,
               ImproperTemperatureValue,
               UnknownDevicePowerState,
               UnknownTeaType
    {
        try
        {
            return _iceI_makeTeaAsync(teaType, temperature, sugarLevel, context, true).waitForResponseOrUserEx();
        }
        catch(ImproperSugarLevelValue ex)
        {
            throw ex;
        }
        catch(ImproperTemperatureValue ex)
        {
            throw ex;
        }
        catch(UnknownDevicePowerState ex)
        {
            throw ex;
        }
        catch(UnknownTeaType ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<Tea> makeTeaAsync(String teaType, short temperature, short sugarLevel)
    {
        return _iceI_makeTeaAsync(teaType, temperature, sugarLevel, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Tea> makeTeaAsync(String teaType, short temperature, short sugarLevel, java.util.Map<String, String> context)
    {
        return _iceI_makeTeaAsync(teaType, temperature, sugarLevel, context, false);
    }

    /**
     * @hidden
     * @param iceP_teaType -
     * @param iceP_temperature -
     * @param iceP_sugarLevel -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Tea> _iceI_makeTeaAsync(String iceP_teaType, short iceP_temperature, short iceP_sugarLevel, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Tea> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "makeTea", null, sync, _iceE_makeTea);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(iceP_teaType);
                     ostr.writeShort(iceP_temperature);
                     ostr.writeShort(iceP_sugarLevel);
                 }, istr -> {
                     Tea ret;
                     ret = Tea.ice_read(istr);
                     return ret;
                 });
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_makeTea =
    {
        ImproperSugarLevelValue.class,
        ImproperTemperatureValue.class,
        UnknownDevicePowerState.class,
        UnknownTeaType.class
    };

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ITeaMachinePrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), ITeaMachinePrx.class, _ITeaMachinePrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ITeaMachinePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), ITeaMachinePrx.class, _ITeaMachinePrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ITeaMachinePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), ITeaMachinePrx.class, _ITeaMachinePrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ITeaMachinePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), ITeaMachinePrx.class, _ITeaMachinePrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static ITeaMachinePrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, ITeaMachinePrx.class, _ITeaMachinePrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static ITeaMachinePrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, ITeaMachinePrx.class, _ITeaMachinePrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default ITeaMachinePrx ice_context(java.util.Map<String, String> newContext)
    {
        return (ITeaMachinePrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default ITeaMachinePrx ice_adapterId(String newAdapterId)
    {
        return (ITeaMachinePrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default ITeaMachinePrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (ITeaMachinePrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default ITeaMachinePrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (ITeaMachinePrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default ITeaMachinePrx ice_invocationTimeout(int newTimeout)
    {
        return (ITeaMachinePrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default ITeaMachinePrx ice_connectionCached(boolean newCache)
    {
        return (ITeaMachinePrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default ITeaMachinePrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (ITeaMachinePrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default ITeaMachinePrx ice_secure(boolean b)
    {
        return (ITeaMachinePrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default ITeaMachinePrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (ITeaMachinePrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default ITeaMachinePrx ice_preferSecure(boolean b)
    {
        return (ITeaMachinePrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default ITeaMachinePrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (ITeaMachinePrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default ITeaMachinePrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (ITeaMachinePrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default ITeaMachinePrx ice_collocationOptimized(boolean b)
    {
        return (ITeaMachinePrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default ITeaMachinePrx ice_twoway()
    {
        return (ITeaMachinePrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default ITeaMachinePrx ice_oneway()
    {
        return (ITeaMachinePrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default ITeaMachinePrx ice_batchOneway()
    {
        return (ITeaMachinePrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default ITeaMachinePrx ice_datagram()
    {
        return (ITeaMachinePrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default ITeaMachinePrx ice_batchDatagram()
    {
        return (ITeaMachinePrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default ITeaMachinePrx ice_compress(boolean co)
    {
        return (ITeaMachinePrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default ITeaMachinePrx ice_timeout(int t)
    {
        return (ITeaMachinePrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default ITeaMachinePrx ice_connectionId(String connectionId)
    {
        return (ITeaMachinePrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default ITeaMachinePrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (ITeaMachinePrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::SmartHome::ITeaMachine";
    }
}
