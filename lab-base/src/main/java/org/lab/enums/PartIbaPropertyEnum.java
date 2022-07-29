package org.lab.enums;

/**
 * 部件IBA属性枚举
 *
 * @author wsj
 * @Date 2022/6/16
 */
public enum PartIbaPropertyEnum {

    /**
     * 供应商名称
     */
    SUPPLIER_NAME ("供应商名称"),

    SUPPLIER ("二极管供应商"),

    MODEL ("型号"),

    SPECIFICATIONS ("规格"),

    COATING ("镀膜"),

    APPEARANCE ("外观"),

    WORKMANSHIP ("工艺"),

    TOUGHENING ("钢化"),

    LONG ("长"),

    WIDE ("宽"),

    WIDTH ("幅宽"),

    THICK ("厚"),

    OPENING_CONDITION ("开孔情况"),

    SPECIAL_REQUIREMENTS ("特殊要求"),

    FILM_TYPE ("胶膜类型"),

    GRAM_WEIGHT("克重"),

    COLOUR ("颜色"),

    STRIP_TYPE ("焊带类型"),

    SIZE ("尺寸"),

    WEIGHT_PER_ROLL ("单卷重量"),

    MIXTURE_RATIO ("配比"),

    CERTIFICATION ("认证情况"),

    DIODE_MODEL ("二极管型号"),

    POSITIVE_NEGATION_ELECTROODES ("正负极"),

    LINE_LENGTH ("线长"),

    CONNECTOR_MODEL ("连接器型号"),

    CABLE_SUPPLIER ("线缆供应商"),

    WIDTH_OF_SIDE_B ("B面宽度"),

    WIDTH_OF_SIDE_C ("C面宽度"),

    TIGER_MOUTH_SIZE ("虎口尺寸"),

    NUMBER_OF_HOLES ("安装孔孔数"),

    TEXTURE_OF_MATERIAL ("材质"),

    OXIDE_FILM_THICKNESS ("氧化膜厚度"),

    PRODUCT_MODEL ("产品型号"),

    PRODUCTION_MODE ("生产模式"),

    NAME_PLATE ("铭牌"),

    RANK_METHODS ("分档方式"),

    QUALITY_CLASSIFY ("质量等级"),

    OEM_INFORMATION ("代工厂信息"),

    PROPERTIES_OF_BATTERY_SHEET_METAL_WIRE ("电池片金刚线属性"),

    SIDE_INFORMATION ("补充信息"),

    FIXED_CONTENT ("固定内容"),

    SIZE_OF_BATTERY_SHEET ("电池片尺寸"),

    TYPE_OF_BATTERY_SHEET ("电池片类型"),

    MAIN_COLUMNS ("主栅数"),

    ONE_SIDED_AND_TWO_SIDED ("单双面"),

    SILICON_NITRIDE ("是否氮氧化硅"),

    ;

    /**
     * 描述
     */
    private final String desc;

    PartIbaPropertyEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
