package top.mylady.model.common.dtos;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * 用于校验分页参数
 */
@Data
@Slf4j
public class PageRequestDto {

    //.private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);

    protected Integer size;
    protected Integer page;

    public void checkParam() {
        if (this.page == null || this.page < 0) {
            setPage(1);
        }
        if (this.size == null || this.size < 0 || this.size > 100) {
            setSize(10);
        }
    }
}
