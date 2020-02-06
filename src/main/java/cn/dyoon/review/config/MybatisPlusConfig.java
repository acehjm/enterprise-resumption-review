package cn.dyoon.review.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author majhdk
 * @DESCRIPTION
 * @date 2018-11-24
 */
@Configuration
@MapperScan("cn.dyoon.review.**.mapper")
public class MybatisPlusConfig {

    /**
     * 配置分页
     * -。xml中可以从里面进行取值,传递参数
     * -。Page 即自动分页,必须放在第一位(可以继承Page实现自己的分页对象)
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
         paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    /**
     * 乐观锁配置
     * -。当要更新一条记录的时候，希望这条记录没有被别人更新
     * 。取出记录时，获取当前version
     * 。更新时，带上这个version
     * 。执行更新时， set version = newVersion where version = oldVersion
     * 。如果version不对，就更新失败
     * -。解实体字段 @Version 必须要
     * 。支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
     * 。整数类型下 newVersion = oldVersion + 1
     * 。newVersion 会回写到 entity 中
     * 。仅支持 updateById(id) 与 update(entity, wrapper) 方法
     * 。在 update(entity, wrapper) 方法下, wrapper 不能复用!!!
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}

