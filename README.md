# The Pit
A simple plugin for the pit game in Minecraft 1.8.

Only using in bungeecord mode.

## Modules
| Modules | Description |
| ------------- | ------------- |
| Listeners  | Handle special events, such as players entering the arena through the jump pad from spawn  |
| Tasks  | Execute planned tasks, such as destroying placed blocks  |
| Items  | Create various materials and weapons  |
| Enchances  | Create various enchances attached to weapons and equipment  |
| Curses  | Provide players with powerful buffs while also offering some debuffs  |
| Buffs  | Provide players with weakly effective buffs  |
| Monsters  | Regularly generate various monsters that will drop resources  |
| Commands  | Create all sorts of commands  |

## API Usage

ListenerModule Exapmle
```java
public class Example extends ListenerModule {//Load ListenerModule

    @Override
    public void listen(Event event) {
    if (event instanceof PlayerJoinEvent e) {//Use "PlayerJoinEvent" event
        Player p = e.getPlayer();
        p.sendMessage("hello!");
    }
}
```

TaskModule Exapmle
```java
public class Example extends TaskModule {//Load TaskModule

    @Override
    public void run(TaskModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {//Use the repeating task from bukkit
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("hello!");//Send "hello" to every player per second
        }
    }, 0, 20);
}
```

CommandModule Exapmle
```java
public class Example extends CommandModule {//Load CommandModule

    @Override
    public String getCommand() {
        return "example";//Set the command to "/example"
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {//Execute "/example" command
        sender.sendMessage("hello");
        return false;
    }
}
```

MonsterModule Exapmle
```java
public class Example extends MonsterModule {//Load MonsterModule

    BossBar boss = new BossBar("");

    @Override
    public boolean isBoss() {//Is it a boss? If so, prevent other bosses from being generated
        return true;
    }

    @Override
    public String getName() {//Set its display name
        return "Slime King";
    }

    @Override
    public String getType() {//Set its identification label
        return "slime_king";
    }

    @Override
    public EntityType getEntityType() {//Set its entity type
        return EntityType.SLIME;
    }

    @Override
    public void listen(Event event) {}

    @Override
    public void run(MonsterModule task) {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {//Spawn monster
            if (!Bukkit.getOnlinePlayers().isEmpty() && !getMonsters().isEmpty()) {
                removeAll();
                for (Location spawns : getMonsterSpawns()) {
                    Slime slime = (Slime) spawnEntity(spawns);
                    slime.setSize(12);
                    slime.setMaxHealth(2000);
                    slime.setHealth(2000);
                    slime.setCanPickupItems(false);
                    slime.setRemoveWhenFarAway(false);
                }
            }
        }, 0, 20);
    }
```

EnchanceModule Exapmle
```java
public class Example extends EnchanceModule {//Load EnchanceModule

    @Override
    public String getEnchance() {//Set identification label of enchancement
        return "example";
    }

    @Override
    public EnchanceType getEnchanceType() {//Set type of enchancement
        return EnchanceType.NORMAL;//Different enchance type Will be bound to different parts
        //Normal means that all parts will be bound
    }

    @Override
    public List<String> getDescription() {
        return Lists.newArrayList("hi");//Set the lore displayed when it is bound to an item
    }

    @Override
    public void listen(Event event) {}

    @Override
    public void run(EnchanceModule task) {}
```

WeaponModule Exapmle
```java
public class WoodenSword extends WeaponModule {//Load WeaponModule

    @Override
    public int getTierPrice(int tier) {//Set the price required to upgrade to a certain level
            switch (tier) {
                case 1 -> {//When upgrade to 1 level player will consume 100 coins
                    return 100;
                default -> {
                    return 0;
                }
            }
        }
    }

    @Override
    public String getTierName(int tier) {//Set the name displayed for upgrading GUI items to a certain level
            switch (tier) {
                case 1 -> {
                    return "hello";
                default -> {
                    return Collections.emptyList();
                }
            }
        }
    }

    @Override
    public List<String> getTierConsumeItems(int tier) {//Set the consume items required to upgrade to a certain level
            switch (tier) {
                case 1 -> {
                    return Lists.newArrayList("ice_block:10");//Consume 10 ice blocks
                default -> {
                    return Collections.emptyList();
                }
            }
        }
    }

    @Override
    public List<String> getTierLore(int tier) {//Set the lores displayed for upgrading GUI items to a certain level
            switch (tier) {
                case 1 -> {
                    return Lists.newArrayList("hello");
                default -> {
                    return Collections.emptyList();
                }
            }
        }
    }

    @Override
    public String getTierUpgradeCostFormat(int tier) {Set the lore displayed on GUI items upgraded to a certain level, for example: spend 100 coins to upgrade to level one
            switch (tier) {
                case 1 -> {
                    return "Spend 100 coins to upgrade to level one";
                default -> {
                    return "";
                }
            }
        }
    }

    @Override
    public String getTierLevelmaxCostFormat(int tier) {Set the lore displayed on GUI items at full level
        if (tier == 4) {//Full level is level 4
            return "level max";
        }
        return "";
    }

    @Override
    public String getType() {
        return "example";
    }

    @Override
    public ItemStack getMenuItem() {
        return WeaponMenuItems.WOODEN_SWORD.getItemStack();
    }

    @Override
    public ItemStack getItem() {
        return WeaponItems.WOODEN_SWORD.getItemStack();
    }

    @Override
    public int getSlot() {
        return 10;
    }

    @Override
    public void listen(Event event) {}

    @Override
    public void run(WeaponModule task) {}
```

MaterialModule Example
```java
public class Example extends MaterialModule {//Load MaterialModule

    @Override
    public String getType() {//Set identification for this material
        return "gold_ingot";
    }

    @Override
    public ItemStack getItem() {//Set the itemstack for item
        return new ItemStack(Material.STONE);
    }

    @Override
    public void listen(Event event) {}//You can set up a listener for the item here, such as blocking the placement of the material

    @Override
    public void run(MaterialModule task) {}
```

BuffModule Example
```java
public class Example extends BuffModule {//Load BuffModule

    @Override
    public ItemStack getMenuItem() {//Set the ItemStack in GUI when the player is not equipped
        return new ItemStack(Material.STONE);
    }

    @Override
    public ItemStack getEquipedItem() {//Set the ItemStack in GUI when the player is equipped
        return new ItemStack(Material.STONE);
    }

    @Override
    public String getType() {//Set the identifier for this Buff
        return "example";
    }

    @Override
    public int getPrice() {//Set the price to unlock this buff
        return 100;
    }

    @Override
    public List<String> getConsumeItems() {//Set the consume items to unlock this buff
        return Lists.newArrayList("ice_block:10");
    }

    @Override
    public List<String> getUnlockedCostFormat() {//Set the lore displayed on the GUI item after unlocking the buff, usually expressed as% cost_form% in the configuration file
        return Lists.newArrayList("Unlocked!");
    }

    @Override
    public List<String> getLockedCostFormat() {//Set the lore displayed on the GUI item when locking the buff, usually expressed as% cost_form% in the configuration file
        return Lists.newArrayList("Locked!");
    }

    @Override
    public void listen(Event event) {}

    @Override
    public void run(BuffModule task) {}
```

CurseModule Example
```java
public class Example extends CurseModule {//Load CurseModule

    @Override
    public ItemStack getMenuItem() {//Set the ItemStack in GUI when the player is not equipped
        return new ItemStack(Material.STONE);
    }

    @Override
    public ItemStack getEquipedItem() {//Set the ItemStack in GUI when the player is equipped
        return new ItemStack(Material.STONE);
    }

    @Override
    public String getType() {//Set the identifier for this Buff
        return "example";
    }

    @Override
    public int getPrice() {//Set the price to unlock this buff
        return 100;
    }

    @Override
    public List<String> getConsumeItems() {//Set the consume items to unlock this buff
        return Lists.newArrayList("ice_block:10");
    }

    @Override
    public List<String> getUnlockedCostFormat() {//Set the lore displayed on the GUI item after unlocking the buff, usually expressed as% cost_form% in the configuration file
        return Lists.newArrayList("Unlocked!");
    }

    @Override
    public List<String> getLockedCostFormat() {//Set the lore displayed on the GUI item when locking the buff, usually expressed as% cost_form% in the configuration file
        return Lists.newArrayList("Locked!");
    }

    @Override
    public void listen(Event event) {}

    @Override
    public void run(BuffModule task) {}
```

Module Register
```java
public class Example extends JavaPlugin {

  @Override
  public void onEnable() {
    new Example().register();//You can register all sorts module in main class
    }
}
```

Regarding various API classes

| Class | Description |
| ------------- | ------------- |
| User  | Obtain various data information of players, such as kills |
| Variables  | Obtain various variable information of the arena, such as "spawn pos" |
| Items  | Obtain various variable information of the registered items |

# Commands

### Basic Commands

| Commands | Description |
| ------------- | ------------- |
| /arena  | Set a world as a arena |
| /dev  | Enter and exit developer mode |
| /item <item> <amount>  | Get a specified number of items |
| /monsterspawn <monster>  | Set a spawn point for a monster |
| /setheight <height>  | Set a max build height for a arena |
| /setspawn  | Set the player's respawn point |
| /spawn  | Teleport to the spawn point (Unavailable in combat) |
| /spawnpos <1|2>  | Select a region and set it as the spawn area |


### Menu Commands

| Commands | Description |
| ------------- | ------------- |
| /armor  | Open armor menu in order to strengthen and upgrade equipment |
| /blocks  | Open blocks menu in order to purchase blocks which can build in arena |
| /buff  | Open buff menu in order to unlock and equip buffs |
| /curse  | Open curse menu in order to unlock and curse buffs |
| /enchance  | Open curse menu in order to enchant equipment |
| /trash  | Open trash menu in order to discard useless items |
| /weapon  | Open weapon menu in order to strengthen and upgrade weapon |
