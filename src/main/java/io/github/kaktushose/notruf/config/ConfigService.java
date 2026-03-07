package io.github.kaktushose.notruf.config;

import de.chojo.sadu.queries.api.call.Call;
import de.chojo.sadu.queries.api.query.Query;
import io.github.kaktushose.notruf.auditlog.lifecycle.Lifecycle;
import io.github.kaktushose.notruf.auditlog.lifecycle.LifecycleService;
import io.github.kaktushose.notruf.auditlog.lifecycle.events.ConfigEvent;
import io.github.kaktushose.notruf.auditlog.model.AuditlogType;
import net.dv8tion.jda.api.entities.UserSnowflake;

import java.util.Optional;

public class ConfigService extends LifecycleService {

    public ConfigService(Lifecycle lifecycle) {
        super(lifecycle);
    }

    public Optional<String> get(BotConfig config) {
        return Query.query("SELECT value FROM configs WHERE name = ?")
                .single(Call.of().bind(config))
                .mapAs(String.class)
                .first();
    }

    public void set(BotConfig config, String value, UserSnowflake issuer) {
        publish(new ConfigEvent(AuditlogType.CONFIG_UPDATE, issuer, config, get(config).orElse(""), value));

        Query.query("INSERT INTO configs (name, value) VALUES (?, ?) ON CONFLICT (name) DO UPDATE SET value = EXCLUDED.value")
                .single(Call.of().bind(config).bind(value))
                .insert();
    }

    public enum BotConfig {
        SERVERLOG_KANAL
    }
}
