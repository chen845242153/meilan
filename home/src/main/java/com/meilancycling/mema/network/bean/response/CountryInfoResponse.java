package com.meilancycling.mema.network.bean.response;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: sorelion qq 571135591
 * @CreateDate: 2020/5/9 17:00
 */
public class CountryInfoResponse {

    /**
     * pageNum : 1
     * pageSize : 1000
     * startRow : 0
     * endRow : 1000
     * total : 245
     * pages : 1
     * rows : [{"id":1,"enName":"Afghanistan","cnName":"阿富汗","cnFirst":"A"},{"id":2,"enName":"Aland Islands","cnName":"奥兰群岛","cnFirst":"A"},{"id":3,"enName":"Albania","cnName":"阿尔巴尼亚","cnFirst":"A"},{"id":4,"enName":"Algeria","cnName":"阿尔及利亚","cnFirst":"A"},{"id":5,"enName":"American Samoa","cnName":"美属萨摩亚","cnFirst":"M"},{"id":6,"enName":"Andorra","cnName":"安道尔","cnFirst":"A"},{"id":7,"enName":"Angola","cnName":"安哥拉","cnFirst":"A"},{"id":8,"enName":"Anguilla","cnName":"安圭拉","cnFirst":"A"},{"id":9,"enName":"Antarctica","cnName":"南极洲","cnFirst":"N"},{"id":10,"enName":"Antigua and Barbuda","cnName":"安提瓜和巴布达","cnFirst":"A"},{"id":11,"enName":"Argentina","cnName":"阿根廷","cnFirst":"A"},{"id":12,"enName":"Armenia","cnName":"亚美尼亚","cnFirst":"Y"},{"id":13,"enName":"Aruba","cnName":"阿鲁巴","cnFirst":"A"},{"id":14,"enName":"Australia","cnName":"澳大利亚","cnFirst":"A"},{"id":15,"enName":"Austria","cnName":"奥地利","cnFirst":"A"},{"id":16,"enName":"Azerbaijan","cnName":"阿塞拜疆","cnFirst":"A"},{"id":17,"enName":"Bahamas","cnName":"巴哈马","cnFirst":"B"},{"id":18,"enName":"Bahrain","cnName":"巴林","cnFirst":"B"},{"id":19,"enName":"Bangladesh","cnName":"孟加拉国","cnFirst":"M"},{"id":20,"enName":"Barbados","cnName":"巴巴多斯","cnFirst":"B"},{"id":21,"enName":"Belarus","cnName":"白俄罗斯","cnFirst":"B"},{"id":22,"enName":"Belgium","cnName":"比利时","cnFirst":"B"},{"id":23,"enName":"Belize","cnName":"伯利兹","cnFirst":"B"},{"id":24,"enName":"Benin","cnName":"贝宁","cnFirst":"B"},{"id":25,"enName":"Bermuda","cnName":"百慕大","cnFirst":"B"},{"id":26,"enName":"Bhutan","cnName":"不丹","cnFirst":"B"},{"id":27,"enName":"Bolivia","cnName":"玻利维亚","cnFirst":"B"},{"id":28,"enName":"Bosnia and Herzegovina","cnName":"波黑","cnFirst":"B"},{"id":29,"enName":"Botswana","cnName":"博茨瓦纳","cnFirst":"B"},{"id":30,"enName":"Bouvet Island","cnName":"布维岛","cnFirst":"B"},{"id":31,"enName":"Brazil","cnName":"巴西","cnFirst":"B"},{"id":32,"enName":"British Indian Ocean Territory (the)","cnName":"英属印度洋领地","cnFirst":"Y"},{"id":33,"enName":"Brunei","cnName":"文莱","cnFirst":"W"},{"id":34,"enName":"Bulgaria","cnName":"保加利亚","cnFirst":"B"},{"id":35,"enName":"Burkina Faso","cnName":"布基纳法索","cnFirst":"B"},{"id":36,"enName":"Burma (Myanmar)","cnName":"缅甸","cnFirst":"M"},{"id":37,"enName":"Burundi","cnName":"布隆迪","cnFirst":"B"},{"id":55,"enName":"Côte d'Ivoire","cnName":"科特迪瓦","cnFirst":"H"},{"id":38,"enName":"Cambodia(Kampuchea)","cnName":"柬埔寨","cnFirst":"J"},{"id":39,"enName":"Cameroon","cnName":"喀麦隆","cnFirst":"K"},{"id":40,"enName":"Canada","cnName":"加拿大","cnFirst":"J"},{"id":41,"enName":"Cape Verde","cnName":"佛得角","cnFirst":"F"},{"id":42,"enName":"Cayman Islands","cnName":"开曼群岛","cnFirst":"K"},{"id":43,"enName":"Central African Republic","cnName":"中非","cnFirst":"Z"},{"id":44,"enName":"Chad","cnName":"乍得","cnFirst":"Z"},{"id":45,"enName":"Chile","cnName":"智利","cnFirst":"Z"},{"id":46,"enName":"China","cnName":"中国","cnFirst":"Z"},{"id":47,"enName":"Christmas Island","cnName":"圣诞岛","cnFirst":"S"},{"id":48,"enName":"Cocos (Keeling) Islands (the)","cnName":"科科斯（基林）群岛","cnFirst":"K"},{"id":49,"enName":"Colombia","cnName":"哥伦比亚","cnFirst":"G"},{"id":50,"enName":"Comoros","cnName":"科摩罗","cnFirst":"K"},{"id":51,"enName":"Congo-Brazzaville","cnName":"刚果（布）","cnFirst":"G"},{"id":52,"enName":"Congo-kinshasa","cnName":"刚果（金）","cnFirst":"G"},{"id":53,"enName":"Cook Islands","cnName":"库克群岛","cnFirst":"K"},{"id":54,"enName":"Costa Rica","cnName":"哥斯达黎加","cnFirst":"G"},{"id":56,"enName":"Croatia","cnName":"克罗地亚","cnFirst":"K"},{"id":57,"enName":"Cuba","cnName":"古巴","cnFirst":"G"},{"id":58,"enName":"Cyprus","cnName":"塞浦路斯","cnFirst":"S"},{"id":59,"enName":"Czech Republic","cnName":"捷克","cnFirst":"J"},{"id":60,"enName":"Denmark","cnName":"丹麦","cnFirst":"D"},{"id":61,"enName":"Djibouti","cnName":"吉布提","cnFirst":"J"},{"id":62,"enName":"Dominica","cnName":"多米尼克","cnFirst":"D"},{"id":63,"enName":"Dominican Republic","cnName":"多米尼加","cnFirst":"D"},{"id":64,"enName":"Ecuador","cnName":"厄瓜多尔","cnFirst":"E"},{"id":65,"enName":"Egypt","cnName":"埃及","cnFirst":"A"},{"id":66,"enName":"El Salvador","cnName":"萨尔瓦多","cnFirst":"S"},{"id":67,"enName":"Equatorial Guinea","cnName":"赤道几内亚","cnFirst":"C"},{"id":68,"enName":"Eritrea","cnName":"厄立特里亚","cnFirst":"E"},{"id":69,"enName":"Estonia","cnName":"爱沙尼亚","cnFirst":"A"},{"id":70,"enName":"Ethiopia","cnName":"埃塞俄比亚","cnFirst":"A"},{"id":71,"enName":"Falkland Islands (the) [Malvinas]","cnName":"福克兰群岛（马尔维纳斯）","cnFirst":"F"},{"id":72,"enName":"Faroe Islands (the)","cnName":"法罗群岛","cnFirst":"F"},{"id":73,"enName":"Fiji","cnName":"斐济","cnFirst":"F"},{"id":74,"enName":"Finland","cnName":"芬兰","cnFirst":"F"},{"id":75,"enName":"France","cnName":"法国","cnFirst":"F"},{"id":76,"enName":"French Guiana","cnName":"法属圭亚那","cnFirst":"F"},{"id":77,"enName":"French Polynesia","cnName":"法属波利尼西亚","cnFirst":"F"},{"id":78,"enName":"French Southern Territories (the)","cnName":"法属南部领地","cnFirst":"F"},{"id":79,"enName":"Gabon","cnName":"加蓬","cnFirst":"J"},{"id":80,"enName":"Gambia","cnName":"冈比亚","cnFirst":"G"},{"id":81,"enName":"Georgia","cnName":"格鲁吉亚","cnFirst":"G"},{"id":82,"enName":"Germany","cnName":"德国","cnFirst":"D"},{"id":83,"enName":"Ghana","cnName":"加纳","cnFirst":"J"},{"id":84,"enName":"Gibraltar","cnName":"直布罗陀","cnFirst":"Z"},{"id":85,"enName":"Greece","cnName":"希腊","cnFirst":"X"},{"id":86,"enName":"Greenland","cnName":"格陵兰","cnFirst":"G"},{"id":87,"enName":"Grenada","cnName":"格林纳达","cnFirst":"G"},{"id":88,"enName":"Guadeloupe","cnName":"瓜德罗普","cnFirst":"G"},{"id":89,"enName":"Guam","cnName":"关岛","cnFirst":"G"},{"id":90,"enName":"Guatemala","cnName":"危地马拉","cnFirst":"W"},{"id":91,"enName":"Guernsey","cnName":"格恩西岛","cnFirst":"G"},{"id":92,"enName":"Guinea","cnName":"几内亚","cnFirst":"J"},{"id":93,"enName":"Guinea-Bissau","cnName":"几内亚比绍","cnFirst":"J"},{"id":94,"enName":"Guyana","cnName":"圭亚那","cnFirst":"G"},{"id":95,"enName":"Haiti","cnName":"海地","cnFirst":"H"},{"id":96,"enName":"Heard Island and McDonald Islands","cnName":"赫德岛和麦克唐纳岛","cnFirst":"H"},{"id":98,"enName":"Honduras","cnName":"洪都拉斯","cnFirst":"H"},{"id":99,"enName":"Hong Kong","cnName":"香港","cnFirst":"X"},{"id":100,"enName":"Hungary","cnName":"匈牙利","cnFirst":"X"},{"id":101,"enName":"Iceland","cnName":"冰岛","cnFirst":"B"},{"id":102,"enName":"India","cnName":"印度","cnFirst":"Y"},{"id":103,"enName":"Indonesia","cnName":"印度尼西亚","cnFirst":"Y"},{"id":104,"enName":"Iran","cnName":"伊朗","cnFirst":"Y"},{"id":105,"enName":"Iraq","cnName":"伊拉克","cnFirst":"Y"},{"id":106,"enName":"Ireland","cnName":"爱尔兰","cnFirst":"A"},{"id":107,"enName":"Isle of Man","cnName":"英国属地曼岛","cnFirst":"Y"},{"id":108,"enName":"Israel","cnName":"以色列","cnFirst":"Y"},{"id":109,"enName":"Italy","cnName":"意大利","cnFirst":"Y"},{"id":110,"enName":"Jamaica","cnName":"牙买加","cnFirst":"Y"},{"id":111,"enName":"Japan","cnName":"日本","cnFirst":"R"},{"id":112,"enName":"Jersey","cnName":"泽西岛","cnFirst":"Z"},{"id":113,"enName":"Jordan","cnName":"约旦","cnFirst":"Y"},{"id":114,"enName":"Kazakhstan","cnName":"哈萨克斯坦","cnFirst":"H"},{"id":115,"enName":"Kenya","cnName":"肯尼亚","cnFirst":"K"},{"id":116,"enName":"Kiribati","cnName":"基里巴斯","cnFirst":"J"},{"id":117,"enName":"Kuwait","cnName":"科威特","cnFirst":"K"},{"id":118,"enName":"Kyrgyzstan","cnName":"吉尔吉斯斯坦","cnFirst":"J"},{"id":119,"enName":"Lao People's Democratic Republic","cnName":"老挝","cnFirst":"L"},{"id":120,"enName":"Latvia","cnName":"拉脱维亚","cnFirst":"L"},{"id":121,"enName":"Lebanon","cnName":"黎巴嫩","cnFirst":"L"},{"id":122,"enName":"Lesotho","cnName":"莱索托","cnFirst":"L"},{"id":123,"enName":"Liberia","cnName":"利比里亚","cnFirst":"L"},{"id":124,"enName":"Libya","cnName":"利比亚","cnFirst":"L"},{"id":125,"enName":"Liechtenstein","cnName":"列支敦士登","cnFirst":"L"},{"id":126,"enName":"Lithuania","cnName":"立陶宛","cnFirst":"L"},{"id":127,"enName":"Luxembourg","cnName":"卢森堡","cnFirst":"L"},{"id":128,"enName":"Macao","cnName":"澳门","cnFirst":"A"},{"id":129,"enName":"Macedonia (the former Yugoslav Republic of)","cnName":"前南马其顿","cnFirst":"Q"},{"id":130,"enName":"Madagascar","cnName":"马达加斯加","cnFirst":"M"},{"id":131,"enName":"Malawi","cnName":"马拉维","cnFirst":"M"},{"id":132,"enName":"Malaysia","cnName":"马来西亚","cnFirst":"M"},{"id":133,"enName":"Maldives","cnName":"马尔代夫","cnFirst":"M"},{"id":134,"enName":"Mali","cnName":"马里","cnFirst":"M"},{"id":135,"enName":"Malta","cnName":"马耳他","cnFirst":"M"},{"id":136,"enName":"Marshall Islands (the)","cnName":"马绍尔群岛","cnFirst":"M"},{"id":137,"enName":"Martinique","cnName":"马提尼克","cnFirst":"M"},{"id":138,"enName":"Mauritania","cnName":"毛利塔尼亚","cnFirst":"M"},{"id":139,"enName":"Mauritius","cnName":"毛里求斯","cnFirst":"M"},{"id":140,"enName":"Mayotte","cnName":"马约特","cnFirst":"M"},{"id":141,"enName":"Mexico","cnName":"墨西哥","cnFirst":"M"},{"id":142,"enName":"Micronesia (the Federated States of)","cnName":"密克罗尼西亚联邦","cnFirst":"M"},{"id":143,"enName":"Moldova","cnName":"摩尔多瓦","cnFirst":"M"},{"id":144,"enName":"Monaco","cnName":"摩纳哥","cnFirst":"M"},{"id":145,"enName":"Mongolia","cnName":"蒙古","cnFirst":"M"},{"id":146,"enName":"Montenegro","cnName":"黑山","cnFirst":"M"},{"id":147,"enName":"Montserrat","cnName":"蒙特塞拉特","cnFirst":"M"},{"id":148,"enName":"Morocco","cnName":"摩洛哥","cnFirst":"M"},{"id":149,"enName":"Mozambique","cnName":"莫桑比克","cnFirst":"M"},{"id":150,"enName":"Namibia","cnName":"纳米比亚","cnFirst":"N"},{"id":151,"enName":"Nauru","cnName":"瑙鲁","cnFirst":"N"},{"id":152,"enName":"Nepal","cnName":"尼泊尔","cnFirst":"N"},{"id":154,"enName":"Netherlands Antilles","cnName":"荷属安的列斯","cnFirst":"H"},{"id":153,"enName":"Netherlands(Holland)","cnName":"荷兰","cnFirst":"H"},{"id":155,"enName":"New Caledonia","cnName":"新喀里多尼亚","cnFirst":"X"},{"id":156,"enName":"New Zealand","cnName":"新西兰","cnFirst":"X"},{"id":157,"enName":"Nicaragua","cnName":"尼加拉瓜","cnFirst":"N"},{"id":158,"enName":"Niger","cnName":"尼日尔","cnFirst":"N"},{"id":159,"enName":"Nigeria","cnName":"尼日利亚","cnFirst":"N"},{"id":160,"enName":"Niue","cnName":"纽埃","cnFirst":"N"},{"id":161,"enName":"Norfolk Island","cnName":"诺福克岛","cnFirst":"N"},{"id":162,"enName":"North Korea","cnName":"朝鲜","cnFirst":"C"},{"id":163,"enName":"Northern Mariana Islands (the)","cnName":"北马里亚纳","cnFirst":"B"},{"id":164,"enName":"Norway","cnName":"挪威","cnFirst":"N"},{"id":165,"enName":"Oman","cnName":"阿曼","cnFirst":"A"},{"id":166,"enName":"Pakistan","cnName":"巴基斯坦","cnFirst":"B"},{"id":167,"enName":"Palau","cnName":"帕劳","cnFirst":"P"},{"id":168,"enName":"Palestinian Territory (the Occupied)","cnName":"巴勒斯坦","cnFirst":"B"},{"id":169,"enName":"Panama","cnName":"巴拿马","cnFirst":"B"},{"id":170,"enName":"Papua New Guinea","cnName":"巴布亚新几内亚","cnFirst":"B"},{"id":171,"enName":"Paraguay","cnName":"巴拉圭","cnFirst":"B"},{"id":172,"enName":"Peru","cnName":"秘鲁","cnFirst":"M"},{"id":173,"enName":"Philippines","cnName":"菲律宾","cnFirst":"F"},{"id":174,"enName":"Pitcairn","cnName":"皮特凯恩","cnFirst":"P"},{"id":175,"enName":"Poland","cnName":"波兰","cnFirst":"B"},{"id":176,"enName":"Portugal","cnName":"葡萄牙","cnFirst":"P"},{"id":177,"enName":"Puerto Rico","cnName":"波多黎各","cnFirst":"B"},{"id":178,"enName":"Qatar","cnName":"卡塔尔","cnFirst":"K"},{"id":180,"enName":"Romania","cnName":"罗马尼亚","cnFirst":"L"},{"id":181,"enName":"Russian Federation","cnName":"俄罗斯联邦","cnFirst":"E"},{"id":182,"enName":"Rwanda","cnName":"卢旺达","cnFirst":"L"},{"id":179,"enName":"Réunion","cnName":"留尼汪","cnFirst":"L"},{"id":183,"enName":"Saint Helena","cnName":"圣赫勒拿","cnFirst":"S"},{"id":184,"enName":"Saint Kitts and Nevis","cnName":"圣基茨和尼维斯","cnFirst":"S"},{"id":185,"enName":"Saint Lucia","cnName":"圣卢西亚","cnFirst":"S"},{"id":186,"enName":"Saint Pierre and Miquelon","cnName":"圣皮埃尔和密克隆","cnFirst":"S"},{"id":187,"enName":"Saint Vincent and the Grenadines","cnName":"圣文森特和格林纳丁斯","cnFirst":"S"},{"id":188,"enName":"Samoa","cnName":"萨摩亚","cnFirst":"S"},{"id":189,"enName":"San Marino","cnName":"圣马力诺","cnFirst":"S"},{"id":190,"enName":"Sao Tome and Principe","cnName":"圣多美和普林西比","cnFirst":"S"},{"id":191,"enName":"Saudi Arabia","cnName":"沙特阿拉伯","cnFirst":"S"},{"id":192,"enName":"Senegal","cnName":"塞内加尔","cnFirst":"S"},{"id":193,"enName":"Serbia","cnName":"塞尔维亚","cnFirst":"S"},{"id":194,"enName":"Seychelles","cnName":"塞舌尔","cnFirst":"S"},{"id":195,"enName":"Sierra Leone","cnName":"塞拉利昂","cnFirst":"S"},{"id":196,"enName":"Singapore","cnName":"新加坡","cnFirst":"X"},{"id":197,"enName":"Slovakia","cnName":"斯洛伐克","cnFirst":"S"},{"id":198,"enName":"Slovenia","cnName":"斯洛文尼亚","cnFirst":"S"},{"id":199,"enName":"Solomon Islands","cnName":"所罗门群岛","cnFirst":"S"},{"id":200,"enName":"Somalia","cnName":"索马里","cnFirst":"S"},{"id":201,"enName":"South Africa","cnName":"南非","cnFirst":"N"},{"id":202,"enName":"South Georgia and the South Sandwich Islands","cnName":"南乔治亚岛和南桑德韦奇岛","cnFirst":"N"},{"id":203,"enName":"South Korea","cnName":"韩国","cnFirst":"H"},{"id":207,"enName":"South Sudan","cnName":"南苏丹","cnFirst":"N"},{"id":204,"enName":"Spain","cnName":"西班牙","cnFirst":"X"},{"id":205,"enName":"Sri Lanka","cnName":"斯里兰卡","cnFirst":"S"},{"id":206,"enName":"Sudan","cnName":"苏丹","cnFirst":"S"},{"id":208,"enName":"Suriname","cnName":"苏里南","cnFirst":"S"},{"id":209,"enName":"Svalbard and Jan Mayen","cnName":"斯瓦尔巴岛和扬马延岛","cnFirst":"S"},{"id":210,"enName":"Swaziland","cnName":"斯威士兰","cnFirst":"S"},{"id":211,"enName":"Sweden","cnName":"瑞典","cnFirst":"R"},{"id":212,"enName":"Switzerland","cnName":"瑞士","cnFirst":"R"},{"id":213,"enName":"Syria","cnName":"叙利亚","cnFirst":"X"},{"id":214,"enName":"Taiwan (Province of China)","cnName":"台湾","cnFirst":"T"},{"id":215,"enName":"Tajikistan","cnName":"塔吉克斯坦","cnFirst":"T"},{"id":216,"enName":"Tanzania","cnName":"坦桑尼亚","cnFirst":"T"},{"id":217,"enName":"Thailand","cnName":"泰国","cnFirst":"T"},{"id":218,"enName":"Timor-Leste","cnName":"东帝汶","cnFirst":"D"},{"id":219,"enName":"Togo","cnName":"多哥","cnFirst":"D"},{"id":220,"enName":"Tokelau","cnName":"托克劳","cnFirst":"T"},{"id":221,"enName":"Tonga","cnName":"汤加","cnFirst":"T"},{"id":222,"enName":"Trinidad and Tobago","cnName":"特立尼达和多巴哥","cnFirst":"T"},{"id":223,"enName":"Tunisia","cnName":"突尼斯","cnFirst":"T"},{"id":224,"enName":"Turkey","cnName":"土耳其","cnFirst":"T"},{"id":225,"enName":"Turkmenistan","cnName":"土库曼斯坦","cnFirst":"T"},{"id":226,"enName":"Turks and Caicos Islands (the)","cnName":"特克斯和凯科斯群岛","cnFirst":"T"},{"id":227,"enName":"Tuvalu","cnName":"图瓦卢","cnFirst":"T"},{"id":228,"enName":"Uganda","cnName":"乌干达","cnFirst":"W"},{"id":229,"enName":"Ukraine","cnName":"乌克兰","cnFirst":"W"},{"id":230,"enName":"United Arab Emirates","cnName":"阿拉伯联合酋长国","cnFirst":"A"},{"id":231,"enName":"United Kingdom","cnName":"英国","cnFirst":"Y"},{"id":232,"enName":"United States","cnName":"美国","cnFirst":"M"},{"id":233,"enName":"United States Minor Outlying Islands","cnName":"美国本土外小岛屿","cnFirst":"M"},{"id":234,"enName":"Uruguay","cnName":"乌拉圭","cnFirst":"W"},{"id":235,"enName":"Uzbekistan","cnName":"乌兹别克斯坦","cnFirst":"W"},{"id":236,"enName":"Vanuatu","cnName":"瓦努阿图","cnFirst":"W"},{"id":97,"enName":"Vaticano","cnName":"梵蒂冈","cnFirst":"F"},{"id":237,"enName":"Venezuela","cnName":"委内瑞拉","cnFirst":"W"},{"id":238,"enName":"VietNam","cnName":"越南","cnFirst":"Y"},{"id":239,"enName":"Virgin Islands (British)","cnName":"英属维尔京群岛","cnFirst":"Y"},{"id":240,"enName":"Virgin Islands (U.S.)","cnName":"美属维尔京群岛","cnFirst":"M"},{"id":241,"enName":"Wallis and Futuna","cnName":"瓦利斯和富图纳","cnFirst":"W"},{"id":242,"enName":"Western Sahara","cnName":"西撒哈拉","cnFirst":"X"},{"id":243,"enName":"Yemen","cnName":"也门","cnFirst":"Y"},{"id":244,"enName":"Zambia","cnName":"赞比亚","cnFirst":"Z"},{"id":245,"enName":"Zimbabwe","cnName":"津巴布韦","cnFirst":"J"}]
     */

    private int pageNum;
    private int pageSize;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    private List<RowsBean> rows;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 1
         * enName : Afghanistan
         * cnName : 阿富汗
         * cnFirst : A
         */

        private int id;
        private String enName;
        private String cnName;
        private String cnFirst;
        private String phone;
        private String nationalFlag;

        public String getNationalFlag() {
            return nationalFlag;
        }

        public void setNationalFlag(String nationalFlag) {
            this.nationalFlag = nationalFlag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        public String getCnName() {
            return cnName;
        }

        public void setCnName(String cnName) {
            this.cnName = cnName;
        }

        public String getCnFirst() {
            return cnFirst;
        }

        public void setCnFirst(String cnFirst) {
            this.cnFirst = cnFirst;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

}
