package top.mylady;


import io.mycat.route.function.AbstractPartitionAlgorithm;

import org.apache.log4j.Logger;


/**
 * 算法设计
 */
public class MyCatBurstRuleAlgorithm extends AbstractPartitionAlgorithm {

    private static Logger logger = Logger.getLogger(MyCatBurstRuleAlgorithm.class);

    //单组数据容量 是每组分片的数据容量
    Long volume;

    //单组DN节点数量 是每组分片的DateNode数量
    Integer step;

    //分片 模是表在每组分片中的节点数量
    Integer mod;

    //分片ID = （dataId/volume）* step +分表ID/mod
    public void init(){

    }

    @Override
    public Integer calculate(String s) {

        if(s != null){
            String[] temp = s.split("-");
            if(temp.length==2){
                try {
                    Long dataId = Long.valueOf(temp[0]);
                    Long burstId = Long.valueOf(temp[1]);
                    int group = (int)(dataId/ volume) * step;
                    int pos = group + (int)(burstId % mod);
                    logger.info("HEIMA RULE INFO ["+s+"]-[{"+pos+"}]");
                    return pos;
                }catch (Exception e){
                    //System.out.println("HEIMA RULE INFO ["+s+"]-[{"+e.getMessage()+"}]");
                    logger.error(String.format("分片加载错误, 错误原因e: %s "+ e + "\r\n HEIMA RULE INFO ["+s+"]-[{"+e.getMessage()+"}]"));
                }
            }
        }
        return 0;
    }


    /**
     * 范围计算
     * @param beginValue 起始
     * @param endValue 结束
     * @return 返回Integer数组
     */
    public Integer[] calculateRange(String beginValue, String endValue){
        if(beginValue!=null&&endValue!=null){
            Integer begin = calculate(beginValue);
            Integer end = calculate(endValue);
            if(begin == null || end == null){
                return new Integer[0];
            }
            if (end >= begin) {
                int len = end - begin + 1;
                Integer[] re = new Integer[len];
                for (int i = 0; i < len; i++) {
                    re[i] = begin + i;
                }
                return re;
            }
        }
        return new Integer[0];
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public void setMod(Integer mod) {
        this.mod = mod;
    }

}
