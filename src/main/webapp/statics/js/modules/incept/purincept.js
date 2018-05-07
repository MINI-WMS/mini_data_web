$(function () {
    var lastsel3;
    $("#jqGrid").jqGrid({
        url: baseURL + 'purincept/list',
        datatype: "local",
        colModel: [
            {label: 'purInceptId', name: 'purInceptId', index: 'pur_incept_id', width: 50, key: true, hidden: true},
            {label: '订货日期', name: 'contractdate', index: 'contractdate', width: 100, editable: true, sorttype: "date"},
            {label: '合同号', name: 'contractno', index: 'contractNo', width: 120, editable: true, edittype: "text"},
            {label: '英文品名', name: 'itemcode', index: 'itemcode', width: 100,},
            {label: '产品名称', name: 'itemname', index: 'itemname', width: 120,},
            {label: '订单号码（PO）', name: 'orderno', index: 'orderNo', width: 150,},
            {label: '序号', name: 'orderseq', index: 'orderseq', width: 80},
            {label: '供应商-卖方', name: 'supplier', index: 'supplier', width: 150},
            {label: '付款方式', name: 'paymentmethod', index: 'paymentmethod', width: 80},
            {
                label: '毛重',
                name: 'grossweight',
                index: 'grossweight',
                width: 80,
                editable: true,
                editrules: {number: true}
            },
            {label: '产地', name: 'productarea', index: 'productarea', width: 80},
            {label: '厂号', name: 'suppliercode', index: 'suppliercode', width: 80},
            {label: '厂名', name: 'suppliername', index: 'suppliername', width: 80},
            {label: '交货港口', name: 'station', index: 'station', width: 80},
            {label: '箱号', name: 'containerno', index: 'containerNo', width: 80},
            {
                label: '提单号', name: 'seq', index: 'seq', width: 150,
                formatter: function (cellvalue, options, rowObject) {
                    var url = "http://tjsee.penavicotj.com/eportaltj/publicquery/im_arrivalquery.aspx";

                    if (rowObject.shipcompany.search("达飞") != -1) {
                        url = "http://www.cma-cgm.com/ebusiness/tracking/search?SearchBy=Booking&search=Search&Reference=" + cellvalue;
                    } else if (rowObject.shipcompany.search("中远") != -1) {
                        url = "http://elines.coscoshipping.com/NewEBWeb/public/cargoTracking/cargoTracking.xhtml?CARGO_TRACKING_NUMBER_TYPE=BILLOFLADING&REDIRECT=1&CARGO_TRACKING_NUMBER=" + cellvalue;
                    } else if (rowObject.shipcompany.search("赫伯罗特") != -1) {
                        url = "https://www.hapag-lloyd.com/zh/online-business/tracing/tracing-by-booking.html?blno=" + cellvalue;
                    } else if (rowObject.shipcompany.search("地中海") != -1) {
                        url = "https://www.msc.com/chn";
                    } else if (rowObject.shipcompany.search("美国总统") != -1 || rowObject.shipcompany.search("APL") != -1 || rowObject.shipcompany.indexof("apl") != -1) {
                        url = "http://homeport.apl.com/gentrack/trackingMain.do?trackInput01=" + cellvalue;
                    } else if (rowObject.shipcompany.search("长荣海运") != -1 || rowObject.shipcompany.search("Evergreen") != -1) {
                        url = "https://www.shipmentlink.com/servlet/TDB1_CargoTracking.do";
                    } else if (rowObject.shipcompany.search("现代") != -1 || rowObject.shipcompany.search("HMM") != -1) {
                        url = "http://www.hmm21.com/cms/business/ebiz/trackTrace/trackTrace/index.jsp?type=1&is_quick=Y&quick_params=&number=" + cellvalue;
                    } else if (rowObject.shipcompany.search("川崎") != -1 || rowObject.shipcompany.search("LINE") != -1) {
                        url = "http://ecom.kline.com/Tracking/Search/FindShipment";
                    } else if (rowObject.shipcompany.search("马士基") != -1 || rowObject.shipcompany.search("MAERSK") != -1) {
                        url = "https://my.maerskline.com/trackingapp/zeroresult?hasError=false&searchNumber=" + cellvalue;
                    } else if (rowObject.shipcompany.search("日本邮船") != -1 || rowObject.shipcompany.search("NYK") != -1) {
                        url = "https://www.nykline.com/ecom/CUP_HOM_3301.do";
                    } else if (rowObject.shipcompany.search("东方海外") != -1 || rowObject.shipcompany.search("OOCL") != -1) {
                        url = "https://www.nykline.com/ecom/CUP_HOM_3301.do";
                    } else if (rowObject.shipcompany.search("三井") != -1 || rowObject.shipcompany.search("MOL") != -1) {
                        url = "http://web.molpower.com/Tracking/Main/Home?Typ=&Flg=Y&Typ=EQP&Dtls=" + cellvalue;
                    } else if (rowObject.shipcompany.search("阳明") != -1 || rowObject.shipcompany.search("yangming") != -1) {
                        url = "https://www.yangming.com/e-service/Track_Trace/blconnect.aspx?rdolType=BL&type=cargo&BLADG=" + cellvalue + ",";
                    }



                    return "<a href='javascript:void(0)'onclick='openUrl(\"" + url + "\");' target=\"_blank\"" +
                        "style='color:blue;cursor:pointer;'>" + cellvalue + "</a>";
                }
            },
            {label: '铅封号', name: 'sealno', index: 'sealNo', width: 80},
            {label: '单据件数', name: 'orderqty', index: 'orderqty', width: 80},
            {label: '净重（kg）', name: 'netweight', index: 'netweight', width: 80},
            {label: '币种', name: 'currency', index: 'currency', width: 80},
            {label: '采购单价', name: 'orderprice', index: 'orderprice', width: 80},
            {label: '发票金额', name: 'billvalue', index: 'billvalue', width: 80},
            {label: '发船日期', name: 'departuredate', index: 'departuredate', width: 80},
            {label: '预计靠港日期', name: 'willarrivedate', index: 'willarrivedate', width: 80},
            {label: '收货人', name: 'receiver', index: 'receiver', width: 80},
            {label: '采购', name: 'purchase', index: 'purchase', width: 80},
            {label: '检疫证核销时间', name: 'quarantinedate', index: 'quarantinedate', width: 80},
            {label: '自动证审批时间', name: 'autocertificatedate', index: 'autocertificatedate', width: 80},
            {label: '自动证号', name: 'autocertificateno', index: 'autocertificateNo', width: 80},
            {label: '卫生证书', name: 'healthcertificate', index: 'healthcertificate', width: 80},
            {label: '生产日期', name: 'productdate', index: 'productdate', width: 80},
            {label: '具体生产日期', name: 'productdatedetail', index: 'productdatedetail', width: 80},
            {
                label: '单据状态',
                name: 'orerstatus',
                index: 'orerstatus',
                width: 80,
                editable: true,
                edittype: "select",
                editoptions: {value: "发货:发货;到港:到港;商检:商检;清关:清关;入库:入库"}
            },
            {label: '代理', name: 'agent', index: 'agent', width: 80},
            {label: '是否索要港口报关费用发票', name: 'isaskforbill', index: 'isaskforbill', width: 80},
            {label: '海关放行时间', name: 'releasedate', index: 'releasedate', width: 80},
            {label: '备注', name: 'remark', index: 'remark', width: 80},
            {label: '船公司', name: 'shipcompany', index: 'shipcompany', width: 80},
            {label: '船名', name: 'shipname', index: 'shipname', width: 80},
            {label: '船次', name: 'shipno', index: 'shipno', width: 80},
            {label: '海运时间(天)', name: 'seadate', index: 'seadate', width: 80},
            {label: '尾款支付日期', name: 'finalmoneydate', index: 'finalmoneydate', width: 80},
            {label: '尾款金额', name: 'finalmoneyvalue', index: 'finalmoneyvalue', width: 80},
            {label: '尾款付款汇率', name: 'finalmoneyrate', index: 'finalmoneyrate', width: 80},
            {label: '定金支付日期', name: 'frontmoneydate', index: 'frontmoneydate', width: 80},
            {label: '定金金额', name: 'frontmoneyvalue', index: 'frontmoneyvalue', width: 80},
            {label: '定金付款汇率', name: 'frontmoneyrate', index: 'frontmoneyrate', width: 80},
            {label: '付汇银行', name: 'bank', index: 'bank', width: 80},
            {label: '收款人', name: 'receivablesuser', index: 'receivablesuser', width: 80},
            {label: '付汇日期', name: 'paymentdate', index: 'paymentdate', width: 80},
            {label: '付汇汇率', name: 'paymentrate', index: 'paymentrate', width: 80},
            {label: '是否已计算货值清算', name: 'iscalculation', index: 'iscalculation', width: 80},
            {label: '是否已付代理费', name: 'ispayagent', index: 'ispayagent', width: 80},
            {label: '申报价格', name: 'declareprice', index: 'declareprice', width: 80},
            {label: '报检号', name: 'inspectionno', index: 'inspectionno', width: 80},
            {label: '报关单号', name: 'declareno', index: 'declareno', width: 80},
            {label: '大宗号码', name: 'bigno', index: 'bigno', width: 80},
            {label: '自动证申请号码', name: 'autocertificateapplyno', index: 'autocertificateapplyNo', width: 80},
            {label: '创建用户', name: 'creatorName', index: 'creator', width: 80},
            {label: '创建时间', name: 'createDate', index: 'create_date', width: 150},
            {label: '修改用户', name: 'modifierName', index: 'modifier', width: 80},
            {label: '修改时间', name: 'modifyDate', index: 'modify_date', width: 150}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        shrinkToFit: false,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
            // ,
            // contractdateStart: vm.q.contractdateStart,
            // contractdateEnd: vm.q.contractdateEnd
            // 'orderno': vm.q.orderno,
            // 'seq': vm.q.seq,
            // 'station': vm.q.station,
            // 'itemname': vm.q.itemname,
            // 'orerstatus': vm.q.orerstatus
        },
        gridComplete: function () {
            $(window).resize();
            //隐藏grid底部滚动条
            // $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        },
        // onSelectRow : function(id) {
        //     if (id && id !== lastsel3) {
        //         jQuery('#jqGrid').jqGrid('restoreRow', lastsel3);
        //         jQuery('#jqGrid').jqGrid('editRow', id, true, pickdates);
        //         lastsel3 = id;
        //     }
        // },
        afterEditCell: function (id, name, val, iRow, iCol) {
            if (name == 'contractdate') {
                jQuery("#" + iRow + "_contractdate", "#jqGrid").datepicker({dateFormat: "yy-mm-dd"});
            }
            // msg(iCol);
        },
        afterSaveCell: function (rowid, name, val, iRow, iCol) {
            // if (name == 'amount') {
            //     var taxval = jQuery("#celltbl").jqGrid('getCell', rowid, iCol + 1);
            //     jQuery("#celltbl").jqGrid('setRowData', rowid, {total: parseFloat(val) + parseFloat(taxval)});
            // }
            // if (name == 'tax') {
            //     var amtval = jQuery("#celltbl").jqGrid('getCell', rowid, iCol - 1);
            //     jQuery("#celltbl").jqGrid('setRowData', rowid, {total: parseFloat(val) + parseFloat(amtval)});
            // }
        },
        cellEdit: true,
        cellsubmit: 'remote',//remote代表每次编辑提交后进行服务器保存，clientArray只保存到数据表格不保存到服务器
        cellurl: baseURL + 'purincept/updateCell'//cellsubmit要提交的后台路径

    });
});
// function pickdates(id) {
//     jQuery("#" + id + "_contractdate", "#jqGrid").datepicker({
//         dateFormat : "yy-mm-dd"
//     });
// }


var vm = new Vue({
        el: '#familyApp',
        data: {
            showList: true,
            title: null,
            purIncept: {},
            q: {
                contractdateStart: getDate(-90),
                contractdateEnd: getCurrentDate(),
                orderno: null,
                seq: null,
                station: null,
                itemname: null,
                orerstatus: ""
            }
        },
        methods: {
            query: function () {
                vm.reload();
            },
            add: function () {
                vm.showList = false;
                vm.title = "新增";
                vm.purIncept = {};
            },
            update: function (event) {
                var purInceptId = getSelectedRow();
                if (purInceptId == null) {
                    return;
                }
                vm.showList = false;
                vm.title = "修改";

                vm.getInfo(purInceptId)
            },
            saveOrUpdate: function (event) {
                var url = vm.purIncept.purInceptId == null ? "purincept/save" : "purincept/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.purIncept),
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            },
            del: function (event) {
                var purInceptIds = getSelectedRows();
                if (purInceptIds == null) {
                    return;
                }

                confirm('确定要删除选中的记录？', function () {
                    $.ajax({
                        type: "POST",
                        url: baseURL + "purincept/delete",
                        contentType: "application/json",
                        data: JSON.stringify(purInceptIds),
                        success: function (r) {
                            if (r.code == 0) {
                                alert('操作成功', function (index) {
                                    $("#jqGrid").trigger("reloadGrid");
                                });
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
                });
            },
            getInfo: function (purInceptId) {
                $.get(baseURL + "purincept/info/" + purInceptId, function (r) {
                    vm.purIncept = r.purIncept;
                });
            },
            reload: function (event) {
                // $("#jqGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
                $("#jqGrid").jqGrid('setGridParam', {datatype: 'json'});
                vm.q.contractdateStart = $("#contractdateStart").val();
                vm.q.contractdateEnd = $("#contractdateEnd").val();

                vm.showList = true;
                var page = $("#jqGrid").jqGrid('getGridParam', 'page');
                $("#jqGrid").jqGrid('setGridParam', {
                    postData: {
                        'contractdateStart': vm.q.contractdateStart,
                        'contractdateEnd': vm.q.contractdateEnd,
                        'orderno': vm.q.orderno,
                        'seq': vm.q.seq,
                        'station': vm.q.station,
                        'itemname': vm.q.itemname,
                        'orerstatus': vm.q.orerstatus
                    },
                    page: page
                }).trigger("reloadGrid");
            },
            editCell: function (event) {
                $("#jqGrid").jqGrid({'cellEdit': true});
            },
            editCellCancel: function (event) {
                $("#jqGrid").jqGrid({'cellEdit': false});

            },
            downloadTemplate: function () {
                //下载模板文件
                var url = baseURL + "download/template/excel?fileName=采购导入表.xlsx";
                // window.open(encodeURI(url));

                downloadFile(url);
            },
            genContractFile: function () {
                //生成采购合同
                var purInceptIds = getSelectedRows();
                if (purInceptIds == null) {
                    return;
                }

                confirm('确定要生成选中记录对应的采购合同？', function () {
                    $.ajax({
                        type: "POST",
                        url: baseURL + "purincept/genContractFile",
                        contentType: "application/json",
                        data: JSON.stringify(purInceptIds),
                        success: function (r) {
                            if (r.code == 0) {
                                var url = baseURL + "download/excel?fileName=" + r.fileName + "&downloadFileName=采购合同.xlsx";
                                msg("采购合同已生成！开始下载...");
                                // window.open(encodeURI(encodeURI(url, "utf-8"), "utf-8"));
                                downloadFile(url);
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
                });
            },
            closeModal: function (event) {

                $('#myModal').modal('hide');
            }
        }
    })
;

var ajaxupload = new AjaxUpload($("#uploadFile"), {
    action: baseURL + "upload/excel",
    type: "POST",
    data: {},
    autoSubmit: true,
    responseType: "json",
    name: 'file',
    onChange: function (file, ext) {//在选中了文件的时候触发
        if (!(ext && /^(xls|xlsx)$/.test(ext))) {
            msg("只支持Excel格式的文件");
        }
    },
    onSubmit: function (file, extension) {//在提交的时候触发
        msg("正在上传文件，请稍候！");
    },
    onComplete: function (file, resp) {//上传结束的时候触发
        if (resp.code === 0) {//excel上传成功，开始excel导入的方法
            $.ajax({
                type: "POST",
                url: baseURL + "purincept/importExcel",
                contentType: "application/json",
                data: JSON.stringify(resp.msg),
                success: function (r) {
                    if (r.code === 0) {
                        alert(r.msg, function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        } else {
            msg("上传失败:" + resp.msg);
        }

    }
});

$(function () {
    $("#contractdateStart").datepicker({dateFormat: "yy-mm-dd"});
    $("#contractdateEnd").datepicker({dateFormat: "yy-mm-dd"});
});


