package com.coriander.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.coriander.auth.service.SysRoleService;
import com.coriander.common.config.exception.CorianderException;
import com.coriander.common.core.BaseController;
import com.coriander.common.result.AjaxResult;
import com.coriander.common.result.HttpStatus;
import com.coriander.common.utils.StringUtil;
import com.coriander.common.utils.page.TableDataInfo;
import com.coriander.model.system.SysRole;
import com.coriander.vo.system.SysRoleQueryVo;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 姓陈的
 * 2023/4/4 16:07
 */
@Api(tags = "角色模块")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController extends BaseController {

    @Resource
    private SysRoleService sysRoleService;


    @ApiOperation("根据id查询角色")
    @GetMapping("/findSysRoleById/{id}")
    public AjaxResult findSysRoleById(@PathVariable Long id){
        SysRole sysRole = sysRoleService.getById(id);
        return success(sysRole);
    }


    /**
     * 分页查询角色列表
     * @param sysRoleQueryVo
     * @return
     */
    @ApiOperation(value = "分页查询接口")
    @GetMapping("/findAll")
    public TableDataInfo findAll(SysRoleQueryVo sysRoleQueryVo){

//        try{int a = 10 / 0;}catch (Exception e){
//            throw new CorianderException(HttpStatus.ERROR,"出现异常拉拉");
//        }
        QueryWrapper<SysRole> qw = new QueryWrapper<>();
        if(!StringUtil.isEmpty(sysRoleQueryVo.getRoleName())){
            qw.like("role_name",sysRoleQueryVo.getRoleName());
        }
        startPage();
        List<SysRole> list = sysRoleService.list(qw);
        return getDataTable(list);
    }

    /**
     * 添加角色
     * @param sysRole
     * @return
     */
    @ApiOperation("添加角色")
    @PostMapping("/addSysRole")
    public AjaxResult addSysRole(@RequestBody SysRole sysRole){
        boolean b = sysRoleService.save(sysRole);
        return toAjax(b);
    }


    /**
     * 更新角色
     * @param sysRole
     * @return
     */
    @ApiOperation("更新角色")
    @PostMapping("/updateSysRole")
    public AjaxResult updateSysRole(@RequestBody SysRole sysRole){

        UpdateWrapper<SysRole> uw = new UpdateWrapper<>();
        if(StringUtil.isNotEmpty(sysRole.getRoleName())){
            uw.set("role_name",sysRole.getRoleName());
        }
        if(StringUtil.isNotEmpty(sysRole.getRoleCode())){
            uw.set("role_code",sysRole.getRoleCode());
        }
        if(StringUtil.isNotNull(sysRole.getDescription())){
            uw.set("description",sysRole.getDescription());
        }
        uw.eq("id",sysRole.getId());

        boolean b = sysRoleService.update(uw);
        return toAjax(b);
    }

    /**
     * 根据id删除角色
     * @param id
     * @return
     */
    @ApiOperation("根据Id删除角色")
    @PostMapping("/delSysRoleById/{id}")
    public AjaxResult delSysRoleById(@PathVariable Long id){
        boolean b = sysRoleService.removeById(id);
        return toAjax(b);
    }

    /**
     * 根据id批量删除角色
     * @param ids
     * @return
     */
    @ApiOperation("根据id批量删除")
    @PostMapping("/batchDelSysRole")
    public AjaxResult batchDelSysRole(@RequestBody List<Long> ids){
        boolean b = sysRoleService.removeByIds(ids);
        return toAjax(b);
    }



}
