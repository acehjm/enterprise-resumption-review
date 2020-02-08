package cn.dyoon.review.domain;

import cn.dyoon.review.domain.entity.ReviewTemplateDocumentDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReviewTemplateDocumentMapper extends BaseMapper<ReviewTemplateDocumentDO> {

    /**
     * 查找重名文件
     * @param fileName
     * @return
     */
    default ReviewTemplateDocumentDO findSameFile(String fileName) {
        return selectOne(Wrappers.<ReviewTemplateDocumentDO>lambdaQuery().eq(ReviewTemplateDocumentDO::getFileName, fileName));
    }
}
