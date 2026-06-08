package plantgame;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import plantgame.GameObject;
import java.awt.Image;
import plantgame.GameUtil;

public class DLCConfigManager {
    private static DLCConfigManager instance;
    
    private Map<String, GameObject> plantTemplates = new HashMap<>();
    private Map<String, GameObject> zombieTemplates = new HashMap<>();
    
    private boolean enabled = false;
    
    private DLCConfigManager() {
        loadConfig();
    }
    
    public static DLCConfigManager getInstance() {
        if (instance == null) {
            instance = new DLCConfigManager();
        }
        return instance;
    }
    
    private void loadConfig() {
        try {
            File configFile = new File("dlc_config.json");
            if (!configFile.exists()) {
                System.out.println("dlc_config.json 不存在，仅加载原版角色。");
                return;
            }
            
            String content = new String(Files.readAllBytes(Paths.get("dlc_config.json")), "UTF-8");
            
            // 简单的 JSON 解析逻辑（为了不引入外部依赖）
            if (content.contains("\"enabled\": true")) {
                this.enabled = true;
            }
            
            if (!this.enabled) return;
            
            // 解析 custom_plants
            parseEntities(content, "custom_plants", plantTemplates, true);
            // 解析 custom_zombies
            parseEntities(content, "custom_zombies", zombieTemplates, false);
            
            System.out.println("DLC 加载成功！植物: " + plantTemplates.size() + ", 僵尸: " + zombieTemplates.size());
            
        } catch (Exception e) {
            System.out.println("加载 dlc_config.json 时出错，优雅降级，仅加载原版角色。" + e.getMessage());
        }
    }
    
    private void parseEntities(String content, String arrayName, Map<String, GameObject> registry, boolean isPlant) {
        Pattern arrayPattern = Pattern.compile("\"" + arrayName + "\"\\s*:\\s*\\[(.*?)\\]", Pattern.DOTALL);
        Matcher arrayMatcher = arrayPattern.matcher(content);
        if (arrayMatcher.find()) {
            String arrayContent = arrayMatcher.group(1);
            Pattern objPattern = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL);
            Matcher objMatcher = objPattern.matcher(arrayContent);
            while (objMatcher.find()) {
                String objContent = objMatcher.group(1);
                GameObject obj = new GameObject();
                
                obj.id = extractString(objContent, "id");
                obj.name = extractString(objContent, "name");
                obj.hp = extractInt(objContent, "hp", 100);
                obj.maxHp = obj.hp;
                obj.setSpeed(extractInt(objContent, "speed", 0));
                obj.damage = extractInt(objContent, "damage", 0);
                obj.sunCost = extractInt(objContent, "sun_cost", 0);
                obj.coolDown = extractInt(objContent, "cool_down", 0);
                
                String spritePath = extractString(objContent, "sprite_path");
                if (spritePath != null) {
                    try {
                        // 使用已有的 GameUtil.getImage，如果没有找到可能需要给默认图
                        // 这里我们使用项目自带的方法
                    } catch(Exception e) {}
                }
                
                String attackBehaviorStr = extractString(objContent, "attack_behavior");
                if (attackBehaviorStr != null) {
                    if ("AOEExplodeBehavior".equals(attackBehaviorStr)) {
                        obj.attackBehavior = new AOEExplodeBehavior();
                    }
                }
                
                String moveBehaviorStr = extractString(objContent, "move_behavior");
                if (moveBehaviorStr != null) {
                    if ("FlyMoveBehavior".equals(moveBehaviorStr)) {
                        obj.moveBehavior = new FlyMoveBehavior();
                    } else if ("MoveBackwardsBehavior".equals(moveBehaviorStr)) {
                        obj.moveBehavior = new MoveBackwardsBehavior();
                    }
                }
                
                if (obj.id != null) {
                    registry.put(obj.id, obj);
                }
            }
        }
    }
    
    private String extractString(String content, String key) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*\"(.*?)\"");
        Matcher m = p.matcher(content);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }
    
    private int extractInt(String content, String key, int def) {
        Pattern p = Pattern.compile("\"" + key + "\"\\s*:\\s*(\\d+)");
        Matcher m = p.matcher(content);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        // 尝试负数
        Pattern p2 = Pattern.compile("\"" + key + "\"\\s*:\\s*(-\\d+)");
        Matcher m2 = p2.matcher(content);
        if (m2.find()) {
            return Integer.parseInt(m2.group(1));
        }
        return def;
    }
    
    public Map<String, GameObject> getPlantTemplates() {
        return plantTemplates;
    }
    
    public Map<String, GameObject> getZombieTemplates() {
        return zombieTemplates;
    }
    
    // 工厂模式：根据 ID 创建新的实体
    public GameObject createPlant(String id) {
        GameObject template = plantTemplates.get(id);
        if (template != null) {
            return cloneGameObject(template);
        }
        return null;
    }
    
    public GameObject createZombie(String id) {
        GameObject template = zombieTemplates.get(id);
        if (template != null) {
            return cloneGameObject(template);
        }
        return null;
    }
    
    private GameObject cloneGameObject(GameObject template) {
        GameObject clone = new GameObject();
        clone.id = template.id;
        clone.name = template.name;
        clone.hp = template.hp;
        clone.maxHp = template.maxHp;
        clone.setSpeed(template.getSpeed());
        clone.damage = template.damage;
        clone.sunCost = template.sunCost;
        clone.coolDown = template.coolDown;
        clone.attackBehavior = template.attackBehavior;
        clone.moveBehavior = template.moveBehavior;
        // 如果有图片等引用也可以在这里拷贝
        return clone;
    }
}
