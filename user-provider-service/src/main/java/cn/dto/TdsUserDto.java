package cn.dto;

import cn.entity.tds.TdsUser;


public class TdsUserDto extends TdsUser{
	
   
 	    private static final long serialVersionUID = -1455996242264031998L;

		private Integer currentPage; // 当前页

		private Integer numPerPage; // 每页显示多行
		
		private Integer totalPage; // 总页数
		
		private Integer totalNumber; // 总条数
		
		

		public int getCurrentPage() {
			return currentPage;
		}

		public void setCurrentPage(Integer currentPage) {
			this.currentPage = currentPage;
		}

		public int getNumPerPage() {
			return numPerPage;
		}

		public void setNumPerPage(Integer numPerPage) {
			this.numPerPage = numPerPage;
		}

		public int getTotalPage() {
			return totalPage;
		}

		public void setTotalPage(Integer totalPage) {
			this.totalPage = totalPage;
		}

		public int getTotalNumber() {
			return totalNumber;
		}

		public void setTotalNumber(Integer totalNumber) {
			this.totalNumber = totalNumber;
		}

		

		
	 public TdsUserDto(Integer currentPage,Integer pageSize){
		 this.currentPage=(currentPage-1)*pageSize;
		 this.numPerPage=pageSize;
		 if(this.currentPage<1)this.currentPage=1;
		 if(this.numPerPage<1)this.numPerPage=10;
	 }

		
	 public TdsUserDto(){
	 }

		
		
}
