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
                text: '理配',
                expanded: true,
                children: [
                    { id: 'receiving-order-mgmt-grid', text: '入库', leaf: true },
                    { id: 'regional-allocation-grid', text: '区域调拨', leaf: true }
                ]
            },
            {
                text: '基础数据管理',
                expanded: true,
                children: [
                    { id: 'company-mgmt-grid', text: '公司管理', leaf: true }
                ]
            }
        ]
    }
});