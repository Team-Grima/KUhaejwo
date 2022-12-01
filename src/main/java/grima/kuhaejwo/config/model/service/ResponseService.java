package grima.kuhaejwo.config.model.service;

import grima.kuhaejwo.config.model.response.CommonResponse;
import grima.kuhaejwo.config.model.response.CommonResult;
import grima.kuhaejwo.config.model.response.ListResult;
import grima.kuhaejwo.config.model.response.SingleResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    //단일건 결과 처리 메소드
    public <T> SingleResult<T> getSingleResult(T data){
        SingleResult<T> singleResult=new SingleResult<>();
        singleResult.setData(data);
        setSuccessResult(singleResult);
        return singleResult;
    }

    //복수건 결과 처리 메소드
    public <T> ListResult<T> getListResult(List<T> list){
        ListResult<T> listResult=new ListResult<>();
        listResult.setData(list);
        setSuccessResult(listResult);
        return listResult;
    }

    //성공 결과만 처리
    public CommonResult getSuccessResult(){
        CommonResult commonResult=new CommonResult();
        setSuccessResult(commonResult);
        return commonResult;
    }

    //실패 결과만 처리
    public CommonResult getFailResult(String msg){
        CommonResult commonResult=new CommonResult();
        setFailResult(commonResult,msg);
        return commonResult;
    }

    //API 요청 성공 시 응답 모델 성공 데이터로 세팅
    private void setSuccessResult(CommonResult commonResult){
        commonResult.setSuccess(true);
        commonResult.setCode(CommonResponse.SUCCESS.getCode());
        commonResult.setMsg(CommonResponse.SUCCESS.getMsg());
    }


    //API 요청 실패 시 응답 모델 실패 데이터로 세팅
    private void setFailResult(CommonResult commonResult,String msg){
        commonResult.setSuccess(false);
        commonResult.setCode(CommonResponse.FAIL.getCode());
        commonResult.setMsg(msg);
    }
}
