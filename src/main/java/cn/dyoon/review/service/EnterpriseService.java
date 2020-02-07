package cn.dyoon.review.service;

import cn.dyoon.review.controller.param.EnterpriseExportParam;
import cn.dyoon.review.controller.param.EnterpriseParam;
import cn.dyoon.review.controller.param.EnterpriseReviewParam;
import cn.dyoon.review.controller.param.EnterpriseSearchParam;
import cn.dyoon.review.controller.vo.EnterpriseInfoVO;
import cn.dyoon.review.controller.vo.EnterpriseListVO;
import cn.dyoon.review.controller.vo.PageVO;
import cn.dyoon.review.manage.auth.constant.UserSession;
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
     * @return
     */
    EnterpriseInfoVO getInfoByUsername(String username);

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
     * @param uploadUserName
     * @param files
     */
    void upload(String enterpriseId, String uploadUserName, List<MultipartFile> files);

    /**
     * 下载文件
     *
     * @param fileId
     * @param response
     * @return
     */
    void download(String fileId, HttpServletResponse response);

    /**
     * 提交申请
     *
     * @param enterpriseId
     */
    void submitApply(String enterpriseId);

    /**
     * 审核通过
     *
     * @param userSession
     * @param param
     */
    void reviewPass(UserSession userSession, EnterpriseReviewParam param);

    /**
     * 审核不通过（退回）
     *
     * @param userSession
     * @param param
     */
    void reviewReturn(UserSession userSession, EnterpriseReviewParam param);

    /**
     * 导出
     *
     * @param param
     */
    void export(EnterpriseExportParam param, HttpServletResponse response);

}
