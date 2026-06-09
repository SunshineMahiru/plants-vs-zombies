const pptxgen = require("pptxgenjs");

let pres = new pptxgen();

// Define Colors based on Forest & Moss theme
const colors = {
    primary: "2C5F2D", // Forest Green
    secondary: "97BC62", // Moss Green
    accent: "F5F5F5", // Cream
    dark: "1E2761",
    text: "333333",
    white: "FFFFFF",
    warning: "B85042"
};

// Layout: 16:9
pres.layout = "LAYOUT_16x9";

// Define Slide Masters
pres.defineSlideMaster({
    title: "MASTER_TITLE",
    background: { color: colors.primary },
    objects: [
        { rect: { x: 0, y: 0, w: "100%", h: "100%", fill: { color: colors.primary } } },
        { rect: { x: "5%", y: "90%", w: "90%", h: 0.1, fill: { color: colors.secondary } } },
        { text: { text: "设计模式期末答辩", options: { x: "5%", y: "92%", w: "50%", color: colors.secondary, fontSize: 12, fontFace: "微软雅黑" } } },
        { text: { text: "2026/06", options: { x: "85%", y: "92%", w: "10%", color: colors.secondary, fontSize: 12, fontFace: "微软雅黑", align: "right" } } }
    ]
});

pres.defineSlideMaster({
    title: "MASTER_CONTENT",
    background: { color: colors.accent },
    objects: [
        { rect: { x: 0, y: 0, w: "100%", h: "15%", fill: { color: colors.primary } } },
        { rect: { x: 0, y: "15%", w: "100%", h: 0.05, fill: { color: colors.secondary } } },
        { text: { text: "设计模式期末答辩", options: { x: "5%", y: "95%", w: "30%", color: "999999", fontSize: 10, fontFace: "微软雅黑" } } },
        { slideNumber: { x: "90%", y: "95%", w: "5%", color: "999999", fontSize: 10, align: "right" } }
    ]
});

pres.defineSlideMaster({
    title: "MASTER_SECTION",
    background: { color: colors.secondary },
    objects: [
        { rect: { x: "0%", y: "0%", w: "40%", h: "100%", fill: { color: colors.primary } } }
    ]
});

// Helper function for titles
const addSlideTitle = (slide, title) => {
    slide.addText(title, { x: "5%", y: "3%", w: "90%", h: "10%", fontSize: 32, fontFace: "微软雅黑", color: colors.white, bold: true, valign: "middle" });
};

// Slide 1: Title
let slide1 = pres.addSlide({ masterName: "MASTER_TITLE" });
slide1.addText("《植物大战僵尸》复刻与架构重构", { x: "10%", y: "35%", w: "80%", fontSize: 48, fontFace: "微软雅黑", color: colors.white, bold: true, align: "center" });
slide1.addText("基于设计模式的子系统优化与功能扩展", { x: "10%", y: "55%", w: "80%", fontSize: 24, fontFace: "微软雅黑", color: colors.secondary, align: "center" });
slide1.addText("项目地址: https://gitee.com/seven-losses-and-eight-losses/plants-vs-zombies", { x: "10%", y: "70%", w: "80%", fontSize: 14, fontFace: "Consolas", color: colors.accent, align: "center" });

// Slide 2: 项目概述与运行环境
let slide2 = pres.addSlide({ masterName: "MASTER_CONTENT" });
addSlideTitle(slide2, "项目概述与运行环境");
slide2.addShape(pres.ShapeType.rect, { x: "5%", y: "25%", w: "40%", h: "60%", fill: { color: colors.white }, shadow: { type: "outer", color: "666666", blur: 5, offset: 3, opacity: 0.3 } });
slide2.addText("项目简介", { x: "8%", y: "28%", w: "34%", fontSize: 24, fontFace: "微软雅黑", color: colors.primary, bold: true });
slide2.addText([
    { text: "复刻经典游戏《植物大战僵尸》", options: { bullet: true, breakLine: true } },
    { text: "重点在于运用设计模式重构代码架构", options: { bullet: true, breakLine: true } },
    { text: "实现高内聚低耦合的子系统", options: { bullet: true, breakLine: true } },
    { text: "支持便捷的功能扩展与新植物/僵尸接入", options: { bullet: true, breakLine: true } }
], { x: "8%", y: "38%", w: "34%", fontSize: 18, fontFace: "微软雅黑", color: colors.text, lineSpacing: 30 });

slide2.addShape(pres.ShapeType.rect, { x: "50%", y: "25%", w: "45%", h: "60%", fill: { color: colors.white }, shadow: { type: "outer", color: "666666", blur: 5, offset: 3, opacity: 0.3 } });
slide2.addText("运行环境", { x: "53%", y: "28%", w: "39%", fontSize: 24, fontFace: "微软雅黑", color: colors.primary, bold: true });
slide2.addText([
    { text: "操作系统: Windows", options: { bullet: true, breakLine: true } },
    { text: "运行方式: 提供可执行的 .exe 文件", options: { bullet: true, breakLine: true } },
    { text: "演示模式: 支持一键启动，直接展示游戏效果", options: { bullet: true, breakLine: true } },
    { text: "版本控制: Gitee托管，文档与代码同步更新", options: { bullet: true, breakLine: true } }
], { x: "53%", y: "38%", w: "39%", fontSize: 18, fontFace: "微软雅黑", color: colors.text, lineSpacing: 30 });

// Slide 3: 核心子系统与流程
let slide3 = pres.addSlide({ masterName: "MASTER_CONTENT" });
addSlideTitle(slide3, "核心子系统与游戏流程");
slide3.addText("游戏主循环 (Main Loop)", { x: "5%", y: "20%", w: "90%", fontSize: 20, fontFace: "微软雅黑", color: colors.primary, bold: true });
slide3.addShape(pres.ShapeType.rightArrow, { x: "10%", y: "30%", w: "15%", h: "10%", fill: { color: colors.secondary }, line: { color: colors.primary, width: 2 } });
slide3.addText("用户输入", { x: "10%", y: "30%", w: "15%", h: "10%", align: "center", fontSize: 16, color: colors.dark, bold: true });
slide3.addShape(pres.ShapeType.rightArrow, { x: "28%", y: "30%", w: "15%", h: "10%", fill: { color: colors.secondary }, line: { color: colors.primary, width: 2 } });
slide3.addText("状态更新", { x: "28%", y: "30%", w: "15%", h: "10%", align: "center", fontSize: 16, color: colors.dark, bold: true });
slide3.addShape(pres.ShapeType.rightArrow, { x: "46%", y: "30%", w: "15%", h: "10%", fill: { color: colors.secondary }, line: { color: colors.primary, width: 2 } });
slide3.addText("碰撞检测", { x: "46%", y: "30%", w: "15%", h: "10%", align: "center", fontSize: 16, color: colors.dark, bold: true });
slide3.addShape(pres.ShapeType.rect, { x: "64%", y: "30%", w: "15%", h: "10%", fill: { color: colors.secondary }, line: { color: colors.primary, width: 2 } });
slide3.addText("画面渲染", { x: "64%", y: "30%", w: "15%", h: "10%", align: "center", fontSize: 16, color: colors.dark, bold: true });

slide3.addText("主要子系统划分", { x: "5%", y: "45%", w: "90%", fontSize: 20, fontFace: "微软雅黑", color: colors.primary, bold: true });
const systems = ["渲染引擎", "物理碰撞", "实体管理", "波次控制", "UI交互"];
systems.forEach((sys, idx) => {
    slide3.addShape(pres.ShapeType.roundRect, { x: 5 + idx * 18 + "%", y: "55%", w: "15%", h: "30%", fill: { color: colors.white }, line: { color: colors.primary, width: 2 } });
    slide3.addText(sys, { x: 5 + idx * 18 + "%", y: "65%", w: "15%", align: "center", fontSize: 18, fontFace: "微软雅黑", color: colors.text, bold: true });
});

// Slide 4: Section - 设计模式应用
let slide4 = pres.addSlide({ masterName: "MASTER_SECTION" });
slide4.addText("01", { x: "5%", y: "20%", w: "30%", fontSize: 72, fontFace: "Arial Black", color: colors.secondary, bold: true });
slide4.addText("设计模式应用", { x: "5%", y: "40%", w: "30%", fontSize: 36, fontFace: "微软雅黑", color: colors.white, bold: true });
slide4.addText("解决代码耦合，提升扩展性", { x: "5%", y: "55%", w: "30%", fontSize: 18, fontFace: "微软雅黑", color: colors.accent });
slide4.addText("简单工厂模式\n装饰器模式\n责任链模式", { x: "45%", y: "35%", w: "50%", fontSize: 28, fontFace: "微软雅黑", color: colors.dark, bold: true, lineSpacing: 40 });

// Slide 5: 简单工厂模式 - 实体创建
let slide5 = pres.addSlide({ masterName: "MASTER_CONTENT" });
addSlideTitle(slide5, "简单工厂模式 (Simple Factory)");
slide5.addText("应用场景：植物与僵尸的创建", { x: "5%", y: "20%", w: "90%", fontSize: 22, fontFace: "微软雅黑", color: colors.primary, bold: true });
slide5.addShape(pres.ShapeType.rect, { x: "5%", y: "30%", w: "40%", h: "55%", fill: { color: colors.white }, line: { color: colors.secondary, width: 2 } });
slide5.addText("重构前 (硬编码)", { x: "5%", y: "32%", w: "40%", align: "center", fontSize: 18, fontFace: "微软雅黑", color: colors.warning, bold: true });
slide5.addText("if (type == 0) {\n  flower.add(new Flower(...));\n} else if (type == 1) {\n  wandou.add(new Wandou(...));\n}\n// 每次新增植物都需要修改此处代码\n// 违反开闭原则", { x: "8%", y: "45%", w: "34%", fontSize: 14, fontFace: "Consolas", color: "555555" });

slide5.addShape(pres.ShapeType.rect, { x: "50%", y: "30%", w: "45%", h: "55%", fill: { color: colors.white }, line: { color: colors.primary, width: 2 } });
slide5.addText("重构后 (简单工厂)", { x: "50%", y: "32%", w: "45%", align: "center", fontSize: 18, fontFace: "微软雅黑", color: colors.primary, bold: true });
slide5.addText("Plant p = PlantFactory.createPlant(type, x, y);\nplantList.add(p);\n\n// 新增植物/僵尸炸弹改动量极少\n// 只需在Factory中注册，无需修改调用方\n// 割草机(LawnMower)也抽象为特殊实体统一管理", { x: "53%", y: "45%", w: "39%", fontSize: 14, fontFace: "Consolas", color: "555555" });

// Slide 6: 装饰器模式 - 植物变异与升级
let slide6 = pres.addSlide({ masterName: "MASTER_CONTENT" });
addSlideTitle(slide6, "装饰器模式 (Decorator)");
slide6.addText("应用场景：植物的动态升级与变异", { x: "5%", y: "20%", w: "90%", fontSize: 22, fontFace: "微软雅黑", color: colors.primary, bold: true });
slide6.addText("需求：两个向日葵变双头、喂豆子变三头射手、坚果变高坚果", { x: "5%", y: "28%", w: "90%", fontSize: 16, fontFace: "微软雅黑", color: colors.text });

slide6.addShape(pres.ShapeType.roundRect, { x: "10%", y: "40%", w: "20%", h: "20%", fill: { color: "E0E0E0" } });
slide6.addText("基础植物\n(BasePlant)", { x: "10%", y: "45%", w: "20%", align: "center", fontSize: 18, fontFace: "微软雅黑", bold: true });

slide6.addShape(pres.ShapeType.rightArrow, { x: "32%", y: "45%", w: "8%", h: "10%", fill: { color: colors.secondary } });

slide6.addShape(pres.ShapeType.roundRect, { x: "42%", y: "40%", w: "20%", h: "20%", fill: { color: colors.secondary } });
slide6.addText("装饰器\n(PlantDecorator)", { x: "42%", y: "45%", w: "20%", align: "center", fontSize: 18, fontFace: "微软雅黑", color: colors.white, bold: true });

slide6.addShape(pres.ShapeType.rightArrow, { x: "64%", y: "45%", w: "8%", h: "10%", fill: { color: colors.primary } });

slide6.addShape(pres.ShapeType.roundRect, { x: "74%", y: "35%", w: "20%", h: "12%", fill: { color: colors.primary } });
slide6.addText("双头向日葵\n(产出x2)", { x: "74%", y: "37%", w: "20%", align: "center", fontSize: 14, fontFace: "微软雅黑", color: colors.white });

slide6.addShape(pres.ShapeType.roundRect, { x: "74%", y: "50%", w: "20%", h: "12%", fill: { color: colors.primary } });
slide6.addText("三头射手\n(多弹道)", { x: "74%", y: "52%", w: "20%", align: "center", fontSize: 14, fontFace: "微软雅黑", color: colors.white });

slide6.addShape(pres.ShapeType.roundRect, { x: "74%", y: "65%", w: "20%", h: "12%", fill: { color: colors.primary } });
slide6.addText("太阳坚果\n(防御+产出)", { x: "74%", y: "67%", w: "20%", align: "center", fontSize: 14, fontFace: "微软雅黑", color: colors.white });

slide6.addText("优点：不修改原类代码，动态附加功能，避免子类爆炸，实现组合特性（如向日葵+坚果）。", { x: "5%", y: "85%", w: "90%", fontSize: 16, fontFace: "微软雅黑", color: colors.primary, bold: true, italic: true });

// Slide 7: 责任链模式 - 波次与碰撞处理
let slide7 = pres.addSlide({ masterName: "MASTER_CONTENT" });
addSlideTitle(slide7, "责任链模式 (Chain of Responsibility)");
slide7.addText("应用场景：僵尸波次控制与碰撞事件分发", { x: "5%", y: "20%", w: "90%", fontSize: 22, fontFace: "微软雅黑", color: colors.primary, bold: true });

slide7.addShape(pres.ShapeType.rect, { x: "5%", y: "30%", w: "42%", h: "50%", fill: { color: colors.white }, line: { color: colors.secondary, width: 2 } });
slide7.addText("解决波次进攻缺陷", { x: "5%", y: "32%", w: "42%", align: "center", fontSize: 18, fontFace: "微软雅黑", color: colors.primary, bold: true });
slide7.addText([
    { text: "原逻辑：硬编码在主循环中判断时间和击杀数", options: { bullet: true, breakLine: true } },
    { text: "重构：构建波次处理器链", options: { bullet: true, breakLine: true } },
    { text: "Wave1 -> Wave2 -> BossWave", options: { bullet: true, breakLine: true } },
    { text: "每个节点判断自己是否满足触发条件，满足则执行，否则传递给下一个节点", options: { bullet: true, breakLine: true } }
], { x: "8%", y: "42%", w: "36%", fontSize: 16, fontFace: "微软雅黑", color: colors.text, lineSpacing: 20 });

slide7.addShape(pres.ShapeType.rect, { x: "50%", y: "30%", w: "45%", h: "50%", fill: { color: colors.white }, line: { color: colors.primary, width: 2 } });
slide7.addText("碰撞与异常状态处理", { x: "50%", y: "32%", w: "45%", align: "center", fontSize: 18, fontFace: "微软雅黑", color: colors.primary, bold: true });
slide7.addText([
    { text: "僵尸倒退怎么处理？", options: { bullet: true, breakLine: true } },
    { text: "状态处理器链：\n冰冻减速 -> 击退效果 -> 正常移动", options: { bullet: true, breakLine: true } },
    { text: "当僵尸受到特定攻击(如大蒜/炸弹冲击波)，将请求传递给击退处理器，改变移动方向坐标", options: { bullet: true, breakLine: true } }
], { x: "53%", y: "42%", w: "39%", fontSize: 16, fontFace: "微软雅黑", color: colors.text, lineSpacing: 20 });

// Slide 8: 趣味性设计与下一步功能
let slide8 = pres.addSlide({ masterName: "MASTER_CONTENT" });
addSlideTitle(slide8, "趣味性功能与体验优化");
slide8.addText("针对用户群体的趣味性设计", { x: "5%", y: "20%", w: "45%", fontSize: 22, fontFace: "微软雅黑", color: colors.primary, bold: true });

slide8.addText([
    { text: "植物变异与融合：向日葵+坚果=太阳坚果，增加策略深度。", options: { bullet: true, breakLine: true } },
    { text: "僵尸特殊行为：增加“飞翔”或“倒退”机制，打破常规防线。", options: { bullet: true, breakLine: true } },
    { text: "UI交互反馈：脑子被吃掉时，增加倒计时或特写动画，增强紧迫感。", options: { bullet: true, breakLine: true } }
], { x: "5%", y: "28%", w: "45%", fontSize: 16, fontFace: "微软雅黑", color: colors.text, lineSpacing: 25 });

slide8.addText("已解决与待办清单", { x: "55%", y: "20%", w: "40%", fontSize: 22, fontFace: "微软雅黑", color: colors.primary, bold: true });
slide8.addShape(pres.ShapeType.rect, { x: "55%", y: "28%", w: "40%", h: "55%", fill: { color: "F9F9F9" }, line: { color: "DDDDDD", width: 1 } });
slide8.addText([
    { text: "✓ 两个向日葵合成双头向日葵", options: { color: colors.primary, breakLine: true } },
    { text: "✓ 豆子道具使豌豆变三头射手", options: { color: colors.primary, breakLine: true } },
    { text: "✓ 完善Menu主菜单与场景切换", options: { color: colors.primary, breakLine: true } },
    { text: "✓ 演示模式一键启动", options: { color: colors.primary, breakLine: true } },
    { text: "□ 优化植物/僵尸的分类基类结构", options: { color: colors.warning, breakLine: true } },
    { text: "□ 完善脑子被吃掉的延迟显示动画", options: { color: colors.warning, breakLine: true } }
], { x: "58%", y: "32%", w: "34%", fontSize: 16, fontFace: "微软雅黑", lineSpacing: 25 });

// Slide 9: 总结与问答
let slide9 = pres.addSlide({ masterName: "MASTER_TITLE" });
slide9.addText("总结与致谢", { x: "10%", y: "35%", w: "80%", fontSize: 48, fontFace: "微软雅黑", color: colors.white, bold: true, align: "center" });
slide9.addText("设计模式让代码更优雅，让扩展更简单", { x: "10%", y: "55%", w: "80%", fontSize: 24, fontFace: "微软雅黑", color: colors.secondary, align: "center" });
slide9.addText("Q & A", { x: "10%", y: "70%", w: "80%", fontSize: 36, fontFace: "Arial Black", color: colors.accent, align: "center" });

// Save the presentation
pres.writeFile({ fileName: "PVZ_Design_Pattern_Presentation.pptx" }).then(fileName => {
    console.log(`created file: ${fileName}`);
});
