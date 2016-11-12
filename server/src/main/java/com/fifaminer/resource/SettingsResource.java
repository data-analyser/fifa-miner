package com.fifaminer.resource;

import com.fifaminer.converter.SettingConfigurationConverter;
import com.fifaminer.service.setting.SettingsService;
import com.fifaminer.service.setting.model.SettingConfigurationTO;
import com.fifaminer.service.setting.type.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/settings")
public class SettingsResource {

    private final SettingsService settingsService;
    private final SettingConfigurationConverter converter;

    @Autowired
    public SettingsResource(SettingsService settingsService,
                            SettingConfigurationConverter converter) {
        this.settingsService = settingsService;
        this.converter = converter;
    }

    @GET
    @Path("/{settingName}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSetting(@PathParam("settingName") Setting setting) {
        return settingsService.getSetting(setting);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSetting(@Valid SettingConfigurationTO settingConfigurationTo) {
        settingsService.updateSetting(converter.fromTO(settingConfigurationTo));
        return Response.ok().build();
    }
}
