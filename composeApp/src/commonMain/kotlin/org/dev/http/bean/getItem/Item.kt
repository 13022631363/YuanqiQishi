package org.dev.http.bean.getItem

import org.dev.http.util.JsonUtil.decodeFromString

sealed class Item{

    abstract val itemInfo: GetItemRequestBody.Settlement.ItemInfo
    data class Card(
        val cardName: String,
        val id: String,
    ): Item() {
        override val itemInfo: GetItemRequestBody.Settlement.ItemInfo by lazy {
             GetItemRequestBody.Settlement.ItemInfo (
                id = "CARD",
                owner = "00000000-0000-0000-0000-000000000000",
                type = 128,
                rate = 0,
                payMethod = 3,
                price = 0,
                season = 0,
            )
        }

        enum class Type (val cards: List<Card>)
        {
            Boss (boss), Monster (monster), Npc (npc)

        }

        companion object
        {
            private val npcJson = "{\n" +
                    "    \"旅行者\":\"E_N_Traveler\",\n" +
                    "    \"巴克斯\":\"NPC_Alchemy\",\n" +
                    "    \"马伦\":\"NPC_Assassin\",\n" +
                    "    \"雷夫\":\"NPC_Berserker\",\n" +
                    "    \"威拉德\":\"NPC_Druid\",\n" +
                    "    \"碧洛迪丝\":\"NPC_Elf\",\n" +
                    "    \"特里斯坦\":\"NPC_Engineer\",\n" +
                    "    \"亚伦\":\"NPC_Knight\",\n" +
                    "    \"霍华德\":\"NPC_Paladin\",\n" +
                    "    \"格洛莉亚\":\"NPC_Priest\",\n" +
                    "    \"兰迪\":\"NPC_Rogue\",\n" +
                    "    \"德里克\":\"NPC_Werewolf\",\n" +
                    "    \"梅\":\"NPC_Witch\",\n" +
                    "    \"布莱姆\":\"NPC_Vampire\"\n" +
                    "}"

            private val bossJson = "{\n" +
                    "    \"恶犬帮首领\":\"E01_B01\",\n" +
                    "    \"野猪王\":\"E01_B02\",\n" +
                    "    \"巨型沼泽螺\":\"E02_B01\",\n" +
                    "    \"曼陀罗花1\":\"E03_B01\",\n" +
                    "    \"精灵曼陀罗\":\"E03_B02\",\n" +
                    "    \"曼陀罗花2\":\"E03_B03\",\n" +
                    "    \"美杜莎\":\"E04_B01\",\n" +
                    "    \"感电史莱姆王\":\"E04_B02\",\n" +
                    "    \"水晶巨蟹\":\"E05_B01\",\n" +
                    "    \"黄金巨蟹\":\"E05_B02\",\n" +
                    "    \"枯骨王\":\"E06_B01\",\n" +
                    "    \"巨大沙虫\":\"E07_B01\",\n" +
                    "    \"阿努比斯\":\"E07_B02\",\n" +
                    "    \"大骑士\":\"E08_B01\",\n" +
                    "    \"大巫师\":\"E08_B02\",\n" +
                    "    \"武器大师\":\"E03_B01\",\n" +
                    "    \"黑暗大骑士\":\"E08_B03\",\n" +
                    "    \"符文巨石 火\":\"E09_B01\",\n" +
                    "    \"符文巨石 冰\":\"E09_B02\",\n" +
                    "    \"符文巨石 雷\":\"E09_B03\",\n" +
                    "    \"符文巨石\":\"E09_B\",\n" +
                    "    \"哥布林大祭司\":\"E10_B01\",\n" +
                    "    \"海盗头目\":\"E11_B01\",\n" +
                    "    \"灰烬火龙\":\"E12_B01\",\n" +
                    "    \"雪山大猿王\":\"E13_B01\",\n" +
                    "    \"巨像 祖兰\":\"E14_B01\",\n" +
                    "    \"维C机甲\":\"E15_B01\",\n" +
                    "    \"瓦克恩\":\"E16_B01\",\n" +
                    "    \"虚空瓦克恩\":\"E16_B02\",\n" +
                    "    \"雪人\":\"E13_B02\",\n" +
                    "    \"雪人王\":\"E13_B03\",\n" +
                    "    \"盘龙雕像\":\"E02_B01\",\n" +
                    "    \"虎豹大当家\":\"Season01_E01_B01\",\n" +
                    "    \"黑化的陈\":\"Season01_E04_B01\",\n" +
                    "    \"武圣之铠\":\"Season01_E04_B02\",\n" +
                    "    \"煞魔\":\"Season01_E05_B01\",\n" +
                    "    \"狂暴煞魔\":\"Season01_E05_B02\"\n" +
                    "}"

            val monsterJson = "{\n" +
                    "    \"宝藏喵\":\"E00_S01\",\n" +
                    "    \"宝藏犬\":\"E00_S02\",\n" +
                    "    \"小野猪\":\"E01_S01\",\n" +
                    "    \"暴躁野猪\":\"E01_S02\",\n" +
                    "    \"土狼战士\":\"E01_S03\",\n" +
                    "    \"土狼斧兵\":\"E01_S04\",\n" +
                    "    \"双斧喽啰\":\"Season01_E01_S01\",\n" +
                    "    \"剑盾喽啰\":\"Season01_E01_S02\",\n" +
                    "    \"射手喽啰\":\"Season01_E01_S03\",\n" +
                    "    \"犀牛鼓手\":\"Season01_E01_S04\",\n" +
                    "    \"骑豹妖术师\":\"Season01_E01_S05\",\n" +
                    "    \"石狮子\":\"Season01_E02_S01\",\n" +
                    "    \"烈火石狮子\":\"Season01_E02_S02\",\n" +
                    "    \"狐人刀客\":\"Season01_E02_S03\",\n" +
                    "    \"狐人吹箭手\":\"Season01_E02_S04\",\n" +
                    "    \"灯笼精\":\"Season01_E02_S05\",\n" +
                    "    \"持戈石人\":\"Season01_E02_S06\",\n" +
                    "    \"方士石人\":\"Season01_E02_S07\",\n" +
                    "    \"武器大师\":\"Season01_E03_B01\",\n" +
                    "    \"蓝衣拳师\":\"Season01_E03_S01\",\n" +
                    "    \"红衣掌师\":\"Season01_E03_S02\",\n" +
                    "    \"绿衣剑师\":\"Season01_E03_S03\",\n" +
                    "    \"双锤武师\":\"Season01_E03_S04\",\n" +
                    "    \"白袍气功师\":\"Season01_E03_S05\",\n" +
                    "    \"木制练武假人\":\"Season01_E03_S06\",\n" +
                    "    \"金属练武假人\":\"Season01_E03_S07\",\n" +
                    "    \"小枫叶树人\":\"Season01_E03_S08\",\n" +
                    "    \"大枫叶树人\":\"Season01_E03_S09\",\n" +
                    "    \"剑盾兵俑\":\"Season01_E04_S01\",\n" +
                    "    \"长戟兵俑\":\"Season01_E04_S02\",\n" +
                    "    \"连弩兵俑\":\"Season01_E04_S03\",\n" +
                    "    \"虎拳兵俑\":\"Season01_E04_S04\",\n" +
                    "    \"残魂灵体\":\"Season01_E04_S05\",\n" +
                    "    \"龙炮兵俑\":\"Season01_E04_S06\",\n" +
                    "    \"将军兵俑\":\"Season01_E04_S07\",\n" +
                    "    \"披甲石狮子\":\"Season01_E04_S08\",\n" +
                    "    \"黑煞爬行者\":\"Season01_E05_S01\",\n" +
                    "    \"黑煞妖\":\"Season01_E05_S02\",\n" +
                    "    \"黑煞兽\":\"Season01_E05_S03\",\n" +
                    "    \"黑煞炮兽\":\"Season01_E05_S04\",\n" +
                    "    \"黑煞飞行者\":\"Season01_E05_S05\",\n" +
                    "    \"黑煞术士\":\"Season01_E05_S06\",\n" +
                    "    \"土狼双刀兵\":\"E01_S05\",\n" +
                    "    \"土狼长刀兵\":\"E01_S06\",\n" +
                    "    \"土狼哨兵\":\"E01_S07\",\n" +
                    "    \"土狼弓兵\":\"E01_S08\",\n" +
                    "    \"史莱姆\":\"E01_S09\",\n" +
                    "    \"草原蝙蝠\":\"E01_S10\",\n" +
                    "    \"潮汐田螺\":\"E02_S01\",\n" +
                    "    \"硬化田螺\":\"E02_S02\",\n" +
                    "    \"沼泽青蛙\":\"E02_S03\",\n" +
                    "    \"沼泽毒蛙\":\"E02_S04\",\n" +
                    "    \"沼泽蚊子\":\"E02_S05\",\n" +
                    "    \"沼泽大蚊\":\"E02_S06\",\n" +
                    "    \"沼泽鱼人\":\"E02_S07\",\n" +
                    "    \"沼泽鱼人战士\":\"E02_S08\",\n" +
                    "    \"森林灰狼\":\"E03_S01\",\n" +
                    "    \"木头树妖\":\"E03_S02\",\n" +
                    "    \"多刺松果\":\"E03_S03\",\n" +
                    "    \"大黄蜂\":\"E03_S04\",\n" +
                    "    \"大紫蜂\":\"E03_S05\",\n" +
                    "    \"豌豆射手\":\"E03_S06\",\n" +
                    "    \"奇异毒花\":\"E03_S07\",\n" +
                    "    \"树妖首领\":\"E03_S08\",\n" +
                    "    \"藤蔓触手\":\"E03_S09\",\n" +
                    "    \"蛇人士兵\":\"E04_S01\",\n" +
                    "    \"蛇人战士\":\"E04_S02\",\n" +
                    "    \"蛇人弩手\":\"E04_S03\",\n" +
                    "    \"蛇人法师\":\"E04_S04\",\n" +
                    "    \"蛇人刺客\":\"E04_S05\",\n" +
                    "    \"萤火巨蜂\":\"E04_S06\",\n" +
                    "    \"巨型电史莱姆\":\"E04_S07\",\n" +
                    "    \"感电史莱姆\":\"E04_S08\",\n" +
                    "    \"哥布林矿工\":\"E05_S01\",\n" +
                    "    \"哥布林投石者\":\"E05_S02\",\n" +
                    "    \"哥布林爆破工\":\"E05_S03\",\n" +
                    "    \"大型蓝晶虫\":\"E05_S04\",\n" +
                    "    \"成年蓝晶虫\":\"E05_S05\",\n" +
                    "    \"幼年蓝晶虫\":\"E05_S06\",\n" +
                    "    \"矿洞史莱姆\":\"E05_S07\",\n" +
                    "    \"矿洞蝙蝠\":\"E05_S08\",\n" +
                    "    \"矿洞咬咬\":\"E05_S09\",\n" +
                    "    \"矿洞石巨人\":\"E05_S10\",\n" +
                    "    \"骨头小兵\":\"E06_S01\",\n" +
                    "    \"骨头射手\":\"E06_S02\",\n" +
                    "    \"骨头战士\":\"E06_S03\",\n" +
                    "    \"骨头犬\":\"E06_S04\",\n" +
                    "    \"游魂\":\"E06_S05\",\n" +
                    "    \"高阶游魂\":\"E06_S06\",\n" +
                    "    \"幽灵\":\"E06_S07\",\n" +
                    "    \"骨头巫师\":\"E06_S08\",\n" +
                    "    \"荒漠刺客\":\"E07_S01\",\n" +
                    "    \"荒漠射手\":\"E07_S02\",\n" +
                    "    \"荒漠法师\":\"E07_S03\",\n" +
                    "    \"木乃伊\":\"E07_S04\",\n" +
                    "    \"木乃伊法师\":\"E07_S05\",\n" +
                    "    \"呆头秃鹫\":\"E07_S06\",\n" +
                    "    \"荒漠蝎子\":\"E07_S07\",\n" +
                    "    \"荒漠豺狼\":\"E07_S08\",\n" +
                    "    \"沙漠石巨人\":\"E07_S09\",\n" +
                    "    \"轮回砂碑\":\"E07_S10\",\n" +
                    "    \"城堡骑士\":\"E08_S01\",\n" +
                    "    \"城堡守卫\":\"E08_S02\",\n" +
                    "    \"城堡弩手\":\"E08_S03\",\n" +
                    "    \"城堡小兵\":\"E08_S04\",\n" +
                    "    \"蓝色小法师\":\"E08_S05\",\n" +
                    "    \"红色小法师\":\"E08_S06\",\n" +
                    "    \"秘术法师\":\"E08_S07\",\n" +
                    "    \"壮硕骑士\":\"E08_S08\",\n" +
                    "    \"壮硕炮手\":\"E08_S09\",\n" +
                    "    \"机关弩\":\"E08_S10\",\n" +
                    "    \"石化飞龙1\":\"E08_S11\",\n" +
                    "    \"石化飞龙2\":\"E08_S12\",\n" +
                    "    \"山地大鸟\":\"E09_S01\",\n" +
                    "    \"山地石巨人\":\"E09_S02\",\n" +
                    "    \"山地石头人\":\"E09_S03\",\n" +
                    "    \"成年石头人\":\"E09_S04\",\n" +
                    "    \"火焰石符文\":\"E09_S05\",\n" +
                    "    \"闪电石符文\":\"E09_S06\",\n" +
                    "    \"冰冻石符文\":\"E09_S07\",\n" +
                    "    \"牛头人士兵\":\"E09_S08\",\n" +
                    "    \"牛头人战士\":\"E09_S09\",\n" +
                    "    \"石符文\":\"E09_S10\",\n" +
                    "    \"部落野猪\":\"E10_S01\",\n" +
                    "    \"哥布林小兵\":\"E10_S02\",\n" +
                    "    \"哥布林射手\":\"E10_S03\",\n" +
                    "    \"哥布林法师\":\"E10_S04\",\n" +
                    "    \"哥布林狂战士\":\"E10_S05\",\n" +
                    "    \"枫叶树妖\":\"E10_S06\",\n" +
                    "    \"爆裂松果\":\"E10_S07\",\n" +
                    "    \"海盗水手\":\"E11_S01\",\n" +
                    "    \"海盗射手\":\"E11_S02\",\n" +
                    "    \"海盗战士\":\"E11_S03\",\n" +
                    "    \"海岛硬刺龟\":\"E11_S04\",\n" +
                    "    \"海岛钢刺龟\":\"E11_S05\",\n" +
                    "    \"鼓鼓刺豚\":\"E11_S06\",\n" +
                    "    \"鼓鼓毒刺豚\":\"E11_S07\",\n" +
                    "    \"海盗鸥\":\"E11_S08\",\n" +
                    "    \"海岛蟹\":\"E11_S09\",\n" +
                    "    \"火山史莱姆\":\"E12_S01\",\n" +
                    "    \"幼年红晶虫\":\"E12_S02\",\n" +
                    "    \"成年红晶虫\":\"E12_S03\",\n" +
                    "    \"大型红晶虫\":\"E12_S04\",\n" +
                    "    \"陨石龟\":\"E12_S05\",\n" +
                    "    \"火山红蜂\":\"E12_S06\",\n" +
                    "    \"煤炭虫\":\"E12_S07\",\n" +
                    "    \"火山怪\":\"E12_S08\",\n" +
                    "    \"火山三头犬\":\"E12_S09\",\n" +
                    "    \"火山石巨人\":\"E12_S10\",\n" +
                    "    \"火山岩\":\"E12_S11\",\n" +
                    "    \"雪山盗贼士兵\":\"E13_S01\",\n" +
                    "    \"雪山盗贼斧手\":\"E13_S02\",\n" +
                    "    \"雪山盗贼法师\":\"E13_S03\",\n" +
                    "    \"雪山盗贼战士\":\"E13_S04\",\n" +
                    "    \"雪山盗贼队长\":\"E13_S05\",\n" +
                    "    \"雪猿\":\"E13_S06\",\n" +
                    "    \"大雪猿\":\"E13_S07\",\n" +
                    "    \"遗迹卫兵\":\"E14_S01\",\n" +
                    "    \"遗迹守卫\":\"E14_S02\",\n" +
                    "    \"遗迹搜寻者\":\"E14_S03\",\n" +
                    "    \"遗迹巨人\":\"E14_S04\",\n" +
                    "    \"远古齿轮\":\"E14_S05\",\n" +
                    "    \"远古齿轮组\":\"E14_S06\",\n" +
                    "    \"遗迹机关\":\"E14_S07\",\n" +
                    "    \"侦查机器人\":\"E15_S01\",\n" +
                    "    \"哨兵犬\":\"E15_S02\",\n" +
                    "    \"搜寻者\":\"E15_S03\",\n" +
                    "    \"驱逐者\":\"E15_S04\",\n" +
                    "    \"机械齿轮\":\"E15_S05\",\n" +
                    "    \"机械齿轮组\":\"E15_S06\",\n" +
                    "    \"机械机关\":\"E15_S07\",\n" +
                    "    \"机械守卫\":\"E15_S08\",\n" +
                    "    \"虚空兽\":\"E16_S01\",\n" +
                    "    \"虚空士兵\":\"E16_S02\",\n" +
                    "    \"虚空炮手\":\"E16_S03\",\n" +
                    "    \"虚空飞船\":\"E16_S04\",\n" +
                    "    \"虚空机器人\":\"E16_S05\",\n" +
                    "    \"虚空战甲\":\"E16_S06\",\n" +
                    "    \"虚空触手\":\"E16_S07\",\n" +
                    "    \"瓦克恩\":\"E16_S08\"\n" +
                    "}"

            val npc = npcJson.decodeFromString<Map<String, String>>().map {
                Card (it.key, it.value)
            }
            val boss = bossJson.decodeFromString<Map<String, String>>().map {
                Card (it.key, it.value)
            }
            val monster = monsterJson.decodeFromString<Map<String, String>>().map {
                Card (it.key, it.value)
            }


        }
    }


    data class Stone(
        val chineseName: String,
        override var itemInfo: GetItemRequestBody.Settlement.ItemInfo
    ): Item() {

        companion object
        {
            fun upgradeStone (): Stone
            {
                val itemInfo = GetItemRequestBody.Settlement.ItemInfo (
                    id = "JEWEL_003",
                    owner = "00000000-0000-0000-0000-000000000000",
                    type = 512,
                    rate = 3,
                    payMethod = 3,
                    price = 500,
                    season = 0,
                    )
                itemInfo.dictNested = GetItemRequestBody.Settlement.ItemInfo.OtherID("UpgradeStone")
                return Stone ("磨炼石",itemInfo)
            }

            fun windRemakeStone() : Stone
            {
                val itemInfo = GetItemRequestBody.Settlement.ItemInfo (
                    id = "JEWEL_001",
                    owner = "00000000-0000-0000-0000-000000000000",
                    type = 512,
                    rate = 3,
                    payMethod = 3,
                    price = 500,
                    season = 0
                )

                itemInfo.dictNested = GetItemRequestBody.Settlement.ItemInfo.OtherID("WindRemakeStone")
                return Stone ("风化石", itemInfo)
            }

            fun fireRemakeStone (): Stone
            {
                val itemInfo = GetItemRequestBody.Settlement.ItemInfo (
                    id = "JEWEL_002",
                    owner = "00000000-0000-0000-0000-000000000000",
                    type = 512,
                    rate = 4,
                    payMethod = 3,
                    price = 500,
                    season = 0

                )
                itemInfo.dictNested = GetItemRequestBody.Settlement.ItemInfo.OtherID("FireRemakeStone")
                return Stone ("火山石",itemInfo)
            }
        }
    }

    data object Feather : Item(){
        val name = "瓦尔基里之羽毛"
        val typeName = "Feather"
        val jewelType = "Feather"
        override val itemInfo: GetItemRequestBody.Settlement.ItemInfo by lazy {
            val itemInfo = GetItemRequestBody.Settlement.ItemInfo (
                id = typeName,
                owner = "00000000-0000-0000-0000-000000000000",
                type = 2048,
                rate = 2,
                payMethod = 3,
                price = 0,
                season = 0
            )
            itemInfo.dictNested = GetItemRequestBody.Settlement.ItemInfo.OtherID (jewelType)
            itemInfo
        }


    }

}


