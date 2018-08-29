package com.donwait.enums;

public enum ProfessionTitle {
	TITLE_CAO("艺术总监"),
	TITLE_CBO("首席品牌官"),
	TITLE_CCO("首席文化官"),
	TITLE_CDO("开发总监"),
	TITLE_CEO("首席执行官"),
	TITLE_CFO("首席财务官"),
	TITLE_CHO("人事总监"),
	TITLE_CIO("首席信息官"),
	TITLE_CKO("首席知识官"),
	TITLE_CMO("首席市场官"),
	TITLE_CNO("首席谈判官"),
	TITLE_COO("首席营运官"),
	TITLE_CPO("公关总监"),
	TITLE_CQO("质量总监"),
	TITLE_CSO("销售总监"),
	TITLE_CTO("首席技术官"),
	TITLE_CVO("评估总监"),
	TITLE_GM("总经理"),
	TITLE_PRESIDENT("总裁"),
	TITLE_VP("副总裁"),
	TITLE_AVP("副总裁助理"),
	TITLE_HRD("人力资源总监"),
	TITLE_OD("运营总监"),
	TITLE_MD("市场总监"),
	TITLE_OM("运作经理"),
	TITLE_PM("产品经理"),
	TITLE_BM("部门经理"),
	TITLE_DM("区域经理"),
	TITLE_RM("区域经理"),
	TITLE_AM("客户经理"),
	TITLE_AP("客户企划"),
	TITLE_ASM("大区销售经理"),
	TITLE_PROGRAMMER("软件程序员"),
	TITLE_TECHNICIAN("电脑技术员"),
	TITLE_DEVELOPMENT("开发工程师"),
	TITLE_FM("财务经理"),
	TITLE_FP("财务计划员"),
	TITLE_ACCOUNT_ASSIST("会计助理"),
	TITLE_AUDITOR("审计员"),
	TITLE_RECEPTIONIST("接待员"),
	TITLE_SECRETARY("秘书"),
	TITLE_CLERK("文员");
	private String text;

	private ProfessionTitle(String text) {
		this.text = text;
	}
	public static Integer parse(String text) {
		ProfessionTitle[] values = ProfessionTitle.values();
		for (ProfessionTitle station : values) {
			if (station.text.equals(text)){
				return station.ordinal();
			}		
		}
		return null;
	};
}
