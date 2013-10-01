Ext.define('Bling.view.Navigation', {
    extend: 'Ext.tree.Panel',
    xtype: 'navigation',
    title: '菜单',
    rootVisible: false,
    lines: false,
    useArrows: true,
    root: {
        expanded: true,
        children: [
						{
							text:'销售',
							expanded:true,
							children: [
								{id:'sell-order-mgmt-grid',text:'销售单维护',leaf:true},
								{id:'pre-order-mgmt-grid',text:'预定单维护',leaf:true},
								{id:'custom-order-mgmt-grid',text:'定制单维护',leaf:true},
								{id:'fix-order-mgmt-grid',text:'维修单',leaf:true},
								{id:'cost-order-mgmt-grid',text:'损耗单',leaf:true},
								{id:'counter-order-mgmt-grid',text:'上柜',leaf:true}
							]
						},
            {
                text: '理配',
                expanded: true,
                children: [
                    { id: 'receiving-order-mgmt-grid', text: '入库', leaf: true },
                    { id: 'regional-allocation-grid', text: '区域调拨', leaf: true },
                    { id: 'distribution-allocation-grid', text: '分销调拨', leaf: true },
                    { id: 'outward-processing-grid', text: '外发', leaf: true },
                    { id: 'price-adjustment-grid', text: '调价', leaf: true }
                ]
            },
            {
                text: '基础数据管理',
                expanded: true,
                children: [
                    { id: 'company-mgmt-grid', text: '公司管理', leaf: true },
                    { id: 'warehouse-mgmt-grid', text: '仓库管理', leaf: true }
                ]
            },
            {
                text: '系统管理',
                expanded: true,
                children: [
                    { id: 'schedule-grid', text: '计划任务', leaf: true }
                ]
            }
        ]
    }
});
