package plantgame;

import plantgame.GameObject;
import plantgame.Card;
import plantgame.GameFrame;
import plantgame.IntroAnimation;
import java.util.Map;
import java.util.ArrayList;

public class EntityFactory {
    public static void loadDLCCards() {
        DLCConfigManager configManager = DLCConfigManager.getInstance();
        Map<String, GameObject> plantTemplates = configManager.getPlantTemplates();
        
        int startIndex = GameFrame.card.size();
        for (Map.Entry<String, GameObject> entry : plantTemplates.entrySet()) {
            GameObject template = entry.getValue();
            // 动态增加卡片位置
            int xOffset = GameFrame.TOOLBAR_X + (int)((86 + startIndex * 65) * GameFrame.TOOLBAR_SCALE);
            int yOffset = GameFrame.cardY + GameFrame.TOOLBAR_Y_BASE;
            
            // 使用默认的图作为卡片图（这里简单处理）
            Card newCard = new Card(xOffset, yOffset,
                GameFrame.CARD_W, GameFrame.CARD_H, template.sunCost, 
                IntroAnimation.cardImages[0], GameFrame.img[13]);
            
            GameFrame.card.add(newCard);
            startIndex++;
        }
    }
    
    public static GameObject createPlant(int index, int gridX, int gridY, int gridNum) {
        DLCConfigManager configManager = DLCConfigManager.getInstance();
        Map<String, GameObject> plantTemplates = configManager.getPlantTemplates();
        int dlcIndex = index - 4; // 原版有0-3
        
        if (dlcIndex >= 0 && dlcIndex < plantTemplates.size()) {
            // 获取对应的 DLC Plant
            String targetId = (String) plantTemplates.keySet().toArray()[dlcIndex];
            GameObject plant = configManager.createPlant(targetId);
            if (plant != null) {
                plant.setX(gridX);
                plant.setY(gridY);
                plant.img = GameFrame.img[13]; // 兜底使用一个默认图以防止空指针
            }
            return plant;
        }
        return null;
    }
}
