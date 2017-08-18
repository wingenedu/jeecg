package org.jeecgframework.core.util;

public class Test {

    /**
     * Json多层数据结构转换成xml
     * @param json
     * @return
     */
    public static String Json2XmlString(String jsonStr){
        System.out.println("json转换成xmlString:");
        net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(jsonStr);
        StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        for(Object key : json.keySet()){
            sb.append("<").append(key).append(">");
            Object value = json.get(key);
            sb.append(iteraorJson(value));
            sb.append("</").append(key).append(">");
        }
        sb.append("</xml>");
        return sb.toString();
    }
    /**
     * 迭代判断value是否还包含jSONObject
     * @param value
     * @return
     */
    public static String iteraorJson(Object value){
        StringBuffer sb = new StringBuffer("");
        if((value.toString().contains(":"))){
            net.sf.json.JSONObject json = ((net.sf.json.JSONObject)value);
            for(Object key : json.keySet()){
                sb.append("<").append(key).append(">");
                Object value2 = json.get(key);
                sb.append(iteraorJson(value2));
                sb.append("</").append(key).append(">");
            }
        }else{
            sb.append(value);
        }
        return sb.toString();
    }





    public static void main(String[] args) {
//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
//                "<response>"+
//                "<head>"+
//                "<ret_flag>90008</ret_flag>"+
//                "<ret_msg>请求报文-报文头-报文为空</ret_msg>"+
//                "<serno>0520170818145553858</serno>"+
//                "<trade_flag></trade_flag>"+
//                "<trade_msg></trade_msg>"+
//                "</head>" +
//                "</response>";

        String xml = HttpUtil.sendUrl("http://14.23.71.164:50114/cmis/CMISHttpService4Channel","","POST","utf-8");
        System.err.println(xml);

        String xml2jsonString = JSONHelper.xml2json(xml);
        System.err.println(xml2jsonString);
        String json2xmlString = JSONHelper.json2xml(xml2jsonString);
        System.err.println(json2xmlString);
//        /* 第一种方法，使用JSON-JAVA提供的方法 */
//        //将xml转为json
//        JSONObject xmlJSONObj = XML.toJSONObject(xml);
//        //设置缩进
//        String jsonPrettyPrintString = xmlJSONObj.toString();
//        //输出格式化后的json
//        System.out.println(jsonPrettyPrintString);
//
//        String json2xmlString = Json2XmlString(jsonPrettyPrintString);
//        System.err.println(json2xmlString);



    }

}
