package cn.dyoon.review.service.impl;

import cn.dyoon.review.controller.param.EnterpriseExportParam;
import cn.dyoon.review.controller.param.EnterpriseParam;
import cn.dyoon.review.controller.param.EnterpriseReviewParam;
import cn.dyoon.review.controller.param.EnterpriseSearchParam;
import cn.dyoon.review.controller.vo.EnterpriseInfoVO;
import cn.dyoon.review.controller.vo.EnterpriseListVO;
import cn.dyoon.review.controller.vo.PageVO;
import cn.dyoon.review.service.EnterpriseService;
import org.apache.ibatis.javassist.bytecode.ByteArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * cn.dyoon.review.service.impl
 *
 * @author majhdk
 * @date 2020/2/7
 */
@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseService enterpriseService;

    @Override
    public void registered(EnterpriseParam param) {

    }

    @Override
    public PageVO<EnterpriseListVO> getPage(EnterpriseSearchParam param) {
        return null;
    }

    @Override
    public EnterpriseInfoVO getInfo(String enterpriseId) {
        return null;
    }

    @Override
    public void delete(String enterpriseId) {

    }

    @Override
    public void upload(String enterpriseId, List<MultipartFile> files) {

    }

    @Override
    public ByteArray download(String fileId) {
        return null;
    }

    @Override
    public void submitApply(String enterpriseId) {

    }

    @Override
    public void review(String username, EnterpriseReviewParam param) {

    }

    @Override
    public void export(EnterpriseExportParam param) {

    }
}
