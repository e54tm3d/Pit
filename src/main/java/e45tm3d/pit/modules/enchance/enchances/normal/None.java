package e45tm3d.pit.modules.enchance.enchances.normal;

import com.google.common.collect.Lists;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.enchance.EnchanceModule;
import e45tm3d.pit.modules.enchance.EnchanceType;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class


None extends EnchanceModule {

    @Override
    public String getIdentifier() {
        return "none";
    }

    @Override
    public EnchanceType getEnchanceType() {
        return EnchanceType.NORMAL;
    }

    @Override
    public List<String> getDescription() {
        List<String> list = new ArrayList<>();
        for (String s : Yaml.ENCHANCE.getConfig().getStringList("enchance.none.description")) {
            list.add(s.replace("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {

    }

    @Override
    public void run(EnchanceModule task) {

    }
}
