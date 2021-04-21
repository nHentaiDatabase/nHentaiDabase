package search;

public class searchEngine {
	
	public searchEngine() {
		
	}
	
	public String[] search(String[][] table, String search, boolean[] config) {
		search = search.toLowerCase();
		String[] searchObj = getSearchObj(search);
		
		/*	config[0] == id
		*	config[1] == title
		*	config[2] == author
		*	config[3] == tags
		*/
		
		boolean safeHook = true;
		for(int i =0;i<config.length;i++) {
			if(config[i] == true)
				safeHook = false;
		}
		
		if(safeHook == true) {
			String[] hook = new String[table.length-1];
			for(int i=0;i<hook.length;i++) {
				hook[i] = table[i][2];
			}
			return hook;
		}
		
		//get the relevant Info from table
		String[][] relevant = new String[table.length-1][4];
		for(int i=0;i<relevant.length;i++) {
			if(config[0] == true) {
				relevant[i][0] = table[i][2];
			}else {
				relevant[i][0] = "";
			}
			if(config[1] == true) {
				relevant[i][1] = table[i][3];
			}else {
				relevant[i][1] = "";
			}
			if(config[2] == true) {
				relevant[i][2] = table[i][4];
			}else {
				relevant[i][2] = "";
			}
			if(config[3] == true) {
				relevant[i][3] = table[i][9];
			}else {
				relevant[i][3] = "";
			}
		}
		
		int[][][] res = new int[relevant.length][searchObj.length][4];
		for(int i=0;i<relevant.length;i++) {
			for(int j=0;j<searchObj.length;j++) {
				for(int k=0;k<relevant[0].length;k++) {
					String tmp = relevant[i][k];
					tmp = tmp.toLowerCase();
					res[i][j][k] = tmp.indexOf(searchObj[j]);
				}
			}
		}
		
		int timesId = 0;
		for(int i=0;i<res.length;i++) {
			for(int j=0;j<res[0].length;j++) {
				for(int k=0;k<res[0][0].length;k++) {
					if(res[i][j][k] >= 0) {
						timesId++;
						k = res[0][0].length;
						j = res[0].length;
					}
				}
			}
		}
		
		String[] resId = new String[timesId];
		int l = 0;
		for(int i=0;i<res.length;i++) {
			for(int j=0;j<res[0].length;j++) {
				for(int k=0;k<res[0][0].length;k++) {
					if(res[i][j][k] >= 0) {
						resId[l] = table[i][2];
						l++;
						k = res[0][0].length;
						j = res[0].length;
					}
				}
			}
		}
		return resId;
	}
	
	public String[] getSearchObj(String search) {
		char[] searchChar = search.toCharArray();
		String[] searchObj;
		int numberObj = 1;
		for(int i=0;i<searchChar.length;i++) {
			if(searchChar[i] == ',') {
				numberObj++;
			}
		}
		searchObj = new String[numberObj];
		int currObj = 0;
		for(int i=0;i<searchChar.length;i++) {
			if(searchChar[i] == ',' && currObj+1<searchObj.length)
				currObj++;
			else {
				if(searchObj[currObj] == null)
					searchObj[currObj] = "" + searchChar[i];
				else
					searchObj[currObj] = searchObj[currObj] + searchChar[i];				
			}
		}
		for(int i=1;i<searchObj.length;i++) {
			String tmp = searchObj[i];
			StringBuilder sb = new StringBuilder(tmp);
			sb.deleteCharAt(0);
			tmp = sb.toString();
			searchObj[i] = tmp;
		}
		return searchObj;
	}
}
