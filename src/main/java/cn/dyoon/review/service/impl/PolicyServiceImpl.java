package cn.dyoon.review.service.impl;

import cn.dyoon.review.controller.param.PolicyParam;
import cn.dyoon.review.controller.param.PolicyPublishParam;
import cn.dyoon.review.controller.vo.PolicyInfoVO;
import cn.dyoon.review.service.PolicyService;
import org.springframework.stereotype.Service;

@Service
public class PolicyServiceImpl implements PolicyService {
    @Override
    public PolicyInfoVO create(PolicyParam param) {
        return null;
    }

    @Override
    public PolicyInfoVO findById(String policyId) {
        return null;
    }

    @Override
    public void deleteById(String policyId) {

    }

    @Override
    public void publish(PolicyPublishParam param) {

    }
}
