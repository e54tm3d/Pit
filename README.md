# The Pit
A simple plugin for the pit game in Minecraft 1.8.

Only using in bungeecord mode.

Share Mode Will coming soon...

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

### ListenerModule Exapmle

Here is the usage of the ListenerModule.

It is usually used to register various listeners.

Listeners are generally used to monitor various special events.

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

### TaskModule Exapmle

Here is the usage of the TaskModule.

It is usually used to register various scheduled tasks.

Scheduled tasks are generally used to handle various data updates.

For example, updates to the Tablist and Scoreboards.

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

### CommandModule Exapmle

Here is the usage of the CommandModule.

It is usually used to register various commands.

Commands are generally used to set various variables or for player activities.

For example, setting the spawn point area of a certain arena, and players entering ```/spawn``` to return to the spawn point.

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

### MonsterModule Exapmle

Here is the usage of the MonsterModule.

It is usually used to create various creatures.

The MonsterModule is generally used to spawn various ordinary monsters and Bosses.

For example, setting up a Boss named Slime King.

If it's an ordinary monster, the Bossbar can be omitted.

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
    public String getIdentifier() {//Set its identification label
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

### EnchanceModule Exapmle

Here is the usage of the EnchanceModule.

It is usually used to create various custom enchantments.

These enchantments are usually different from Vanilla enchantments.

The enchantments here require the consumption of materials and coins (Vault API).

```java
public class Example extends EnchanceModule {//Load EnchanceModule

    @Override
    public String getIdentifier() {//Set identification label of enchancement
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

### WeaponModule Exapmle

Here is the usage of the WeaponModule.

It is usually used to create various custom weapons.

These weapons are often accompanied by special effects.

You can set various materials required for upgrades here.

The maximum level of a Weapon is 4, and levels above 4 usually won't be displayed.

```java
public class WoodenSword extends WeaponModule {//Load WeaponModule

    @Override
    public WeaponType getType() {
        return WeaponType.NORMAL;//Set the weapon type if it is "AMULET", enchancements will not impact it
    }

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
    public String getIdentifier() {
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

### MaterialModule Example

Here is the usage of the MaterialModule.

It is usually used to create various custom Materials.

You can set various special events in it.

For example, if the original item of a Material is a certain block, you can prevent players from placing that block.

```java
public class Example extends MaterialModule {//Load MaterialModule

    @Override
    public String getIdentifier() {//Set identification for this material
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

### BuffModule Example

Here is the usage of the BuffModule.

It is usually used to create various custom Buffs.

You can customize the buffs you want in it.

Generally speaking, players can only equip 5 buffs.

These buffs are usually not particularly powerful and there are no debuffs.

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
    public String getIdentifier() {//Set the identifier for this Buff
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

### CurseModule Example

Here is the usage of the CurseModule.

It is usually used to create various custom curses.

You can customize the curses you want in it.

Generally speaking, players can only equip 3 curses.

These curses are usually accompanied by powerful buffs and some debuffs.

For example, the greater the distance, the higher the player's damage to enemies, and vice versa (from ```Calamity Mod``` for ```Terraria```).

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
    public String getIdentifier() {//Set the identifier for this Buff
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

### Module Register

The registration format for each module is generally as follows.

```java
public class Example extends JavaPlugin {

  @Override
  public void onEnable() {
    new Example().register();//You can register all sorts module in main class
    }
}
```

### Regarding various API classes

Here are multiple API-related Classes provided, which are usually used to handle various variables and data.

| Class | Description |
| ------------- | ------------- |
| User  | Obtain various data information of players, such as kills |
| Variables  | Obtain various variable information of the arena, such as "spawn pos" |
| Items  | Obtain various variable information of the registered items |
| Monster  | Obtain various variable information of the Monsters |

# Commands

The usage of various commands will be introduced here.

### Basic Commands

Here is the usage of Basic Commands.

They are generally used to handle player data or player activities.

| Commands | Description |
| ------------- | ------------- |
| /arena  | Set a world as a arena |
| /dev  | Enter and exit developer mode |
| /item <item> <amount>  | Get a specified number of items |
| /monsterspawn <monster>  | Set a spawn point for a monster |
| /setheight <height>  | Set a max build height for a arena |
| /setspawn  | Set the player's respawn point |
| /spawn  | Teleport to the spawn point (Unavailable in combat) |
| /spawnpos <1/2>  | Select a region and set it as the spawn area |


### Menu Commands

Here is the usage of Menu Commands.

They are generally used to handle events where players open menus.

| Commands | Description |
| ------------- | ------------- |
| /armor  | Open armor menu in order to strengthen and upgrade equipment |
| /blocks  | Open blocks menu in order to purchase blocks which can build in arena |
| /buff  | Open buff menu in order to unlock and equip buffs |
| /curse  | Open curse menu in order to unlock and curse buffs |
| /enchance  | Open curse menu in order to enchant equipment |
| /trash  | Open trash menu in order to discard useless items |
| /weapon  | Open weapon menu in order to strengthen and upgrade weapon |
