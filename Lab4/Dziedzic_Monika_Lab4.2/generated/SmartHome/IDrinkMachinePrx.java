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

public interface IDrinkMachinePrx extends IDevicePrx
{
    default Tea makeTea(String drinkType, String teaType, short temperature, short sugarLevel)
        throws ImproperSugarLevelValue,
               ImproperTemperatureValue,
               UnknownDevicePowerState,
               UnknownDrinkType,
               UnknownTeaType
    {
        return makeTea(drinkType, teaType, temperature, sugarLevel, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default Tea makeTea(String drinkType, String teaType, short temperature, short sugarLevel, java.util.Map<String, String> context)
        throws ImproperSugarLevelValue,
               ImproperTemperatureValue,
               UnknownDevicePowerState,
               UnknownDrinkType,
               UnknownTeaType
    {
        try
        {
            return _iceI_makeTeaAsync(drinkType, teaType, temperature, sugarLevel, context, true).waitForResponseOrUserEx();
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
        catch(UnknownDrinkType ex)
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

    default java.util.concurrent.CompletableFuture<Tea> makeTeaAsync(String drinkType, String teaType, short temperature, short sugarLevel)
    {
        return _iceI_makeTeaAsync(drinkType, teaType, temperature, sugarLevel, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Tea> makeTeaAsync(String drinkType, String teaType, short temperature, short sugarLevel, java.util.Map<String, String> context)
    {
        return _iceI_makeTeaAsync(drinkType, teaType, temperature, sugarLevel, context, false);
    }

    /**
     * @hidden
     * @param iceP_drinkType -
     * @param iceP_teaType -
     * @param iceP_temperature -
     * @param iceP_sugarLevel -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Tea> _iceI_makeTeaAsync(String iceP_drinkType, String iceP_teaType, short iceP_temperature, short iceP_sugarLevel, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Tea> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "makeTea", null, sync, _iceE_makeTea);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(iceP_drinkType);
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
        UnknownDrinkType.class,
        UnknownTeaType.class
    };

    default Chocolate makeChocolate(String drinkType, String chocolateType, short temperature)
        throws ImproperTemperatureValue,
               UnknownChocolateType,
               UnknownDevicePowerState,
               UnknownDrinkType
    {
        return makeChocolate(drinkType, chocolateType, temperature, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default Chocolate makeChocolate(String drinkType, String chocolateType, short temperature, java.util.Map<String, String> context)
        throws ImproperTemperatureValue,
               UnknownChocolateType,
               UnknownDevicePowerState,
               UnknownDrinkType
    {
        try
        {
            return _iceI_makeChocolateAsync(drinkType, chocolateType, temperature, context, true).waitForResponseOrUserEx();
        }
        catch(ImproperTemperatureValue ex)
        {
            throw ex;
        }
        catch(UnknownChocolateType ex)
        {
            throw ex;
        }
        catch(UnknownDevicePowerState ex)
        {
            throw ex;
        }
        catch(UnknownDrinkType ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<Chocolate> makeChocolateAsync(String drinkType, String chocolateType, short temperature)
    {
        return _iceI_makeChocolateAsync(drinkType, chocolateType, temperature, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Chocolate> makeChocolateAsync(String drinkType, String chocolateType, short temperature, java.util.Map<String, String> context)
    {
        return _iceI_makeChocolateAsync(drinkType, chocolateType, temperature, context, false);
    }

    /**
     * @hidden
     * @param iceP_drinkType -
     * @param iceP_chocolateType -
     * @param iceP_temperature -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Chocolate> _iceI_makeChocolateAsync(String iceP_drinkType, String iceP_chocolateType, short iceP_temperature, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Chocolate> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "makeChocolate", null, sync, _iceE_makeChocolate);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(iceP_drinkType);
                     ostr.writeString(iceP_chocolateType);
                     ostr.writeShort(iceP_temperature);
                 }, istr -> {
                     Chocolate ret;
                     ret = Chocolate.ice_read(istr);
                     return ret;
                 });
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_makeChocolate =
    {
        ImproperTemperatureValue.class,
        UnknownChocolateType.class,
        UnknownDevicePowerState.class,
        UnknownDrinkType.class
    };

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static IDrinkMachinePrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), IDrinkMachinePrx.class, _IDrinkMachinePrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static IDrinkMachinePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), IDrinkMachinePrx.class, _IDrinkMachinePrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static IDrinkMachinePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), IDrinkMachinePrx.class, _IDrinkMachinePrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static IDrinkMachinePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), IDrinkMachinePrx.class, _IDrinkMachinePrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static IDrinkMachinePrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, IDrinkMachinePrx.class, _IDrinkMachinePrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static IDrinkMachinePrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, IDrinkMachinePrx.class, _IDrinkMachinePrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default IDrinkMachinePrx ice_context(java.util.Map<String, String> newContext)
    {
        return (IDrinkMachinePrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default IDrinkMachinePrx ice_adapterId(String newAdapterId)
    {
        return (IDrinkMachinePrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default IDrinkMachinePrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (IDrinkMachinePrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default IDrinkMachinePrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (IDrinkMachinePrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default IDrinkMachinePrx ice_invocationTimeout(int newTimeout)
    {
        return (IDrinkMachinePrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default IDrinkMachinePrx ice_connectionCached(boolean newCache)
    {
        return (IDrinkMachinePrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default IDrinkMachinePrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (IDrinkMachinePrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default IDrinkMachinePrx ice_secure(boolean b)
    {
        return (IDrinkMachinePrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default IDrinkMachinePrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (IDrinkMachinePrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default IDrinkMachinePrx ice_preferSecure(boolean b)
    {
        return (IDrinkMachinePrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default IDrinkMachinePrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (IDrinkMachinePrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default IDrinkMachinePrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (IDrinkMachinePrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default IDrinkMachinePrx ice_collocationOptimized(boolean b)
    {
        return (IDrinkMachinePrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default IDrinkMachinePrx ice_twoway()
    {
        return (IDrinkMachinePrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default IDrinkMachinePrx ice_oneway()
    {
        return (IDrinkMachinePrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default IDrinkMachinePrx ice_batchOneway()
    {
        return (IDrinkMachinePrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default IDrinkMachinePrx ice_datagram()
    {
        return (IDrinkMachinePrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default IDrinkMachinePrx ice_batchDatagram()
    {
        return (IDrinkMachinePrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default IDrinkMachinePrx ice_compress(boolean co)
    {
        return (IDrinkMachinePrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default IDrinkMachinePrx ice_timeout(int t)
    {
        return (IDrinkMachinePrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default IDrinkMachinePrx ice_connectionId(String connectionId)
    {
        return (IDrinkMachinePrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default IDrinkMachinePrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (IDrinkMachinePrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::SmartHome::IDrinkMachine";
    }
}
