package cn.dyoon.review.service;

import cn.dyoon.review.controller.param.EnterpriseExportParam;
import cn.dyoon.review.controller.param.EnterpriseParam;
import cn.dyoon.review.controller.param.EnterpriseReviewParam;
import cn.dyoon.review.controller.param.EnterpriseSearchParam;
import cn.dyoon.review.controller.vo.EnterpriseInfoVO;
import cn.dyoon.review.controller.vo.EnterpriseListVO;
import cn.dyoon.review.controller.vo.PageVO;
import org.apache.ibatis.javassist.bytecode.ByteArray;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * cn.dyoon.review.service
 *
 * @author majhdk
 * @date 2020/2/6
 */
public interface EnterpriseService {

    /**
     * 注册用户
     *
     * @param param
     */
    void registered(EnterpriseParam param);

    /**
     * 分页获取企业列表
     *
     * @param param
     * @return
     */
    PageVO<EnterpriseListVO> getPage(EnterpriseSearchParam param);

    /**
     * 获取企业详情
     *
     * @param enterpriseId
     * @return
     */
    EnterpriseInfoVO getInfo(String enterpriseId);

    /**
     * 获取企业详情
     *
     * @param username
     * @param enterpriseId
     * @return
     */
    EnterpriseInfoVO getInfo(String username, String enterpriseId);

    /**
     * 删除企业
     *
     * @param enterpriseId
     */
    void delete(String enterpriseId);

    /**
     * 上传文件
     *
     * @param enterpriseId
     * @param files
     */
    void upload(String enterpriseId, List<MultipartFile> files);

    /**
     * 下载文件
     *
     * @param fileId
     * @param response
     * @return
     */
    ByteArray download(String fileId, HttpServletResponse response);

    /**
     * 提交申请
     *
     * @param enterpriseId
     */
    void submitApply(String enterpriseId);

    /**
     * 审核
     *
     * @param username
     * @param param
     */
    void review(String username, EnterpriseReviewParam param);

    /**
     * 导出
     *
     * @param param
     */
    void export(EnterpriseExportParam param, HttpServletResponse response);

}
