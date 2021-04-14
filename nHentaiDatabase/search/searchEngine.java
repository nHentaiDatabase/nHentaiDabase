package search;

public class searchEngine {
	
	public searchEngine() {
		/*String[][] test = new String[][]{{"177013", "Methaphorsis", "Shindol", "loli, breast"},
										 {"123", "test", "author", "joink, jee, loli"},
										 {"1234", "test", "author", "joink, jee, loli"}};
										 
		String search = "test, loli, jee, 123, 177013";
		String[] Arr = search(test, search);
		for(int i=0;i<Arr.length;i++) {
			System.out.println(Arr[i]);
		}*/
	}
	
	public String[] search(String[][] table, String search) {
		search = search.toLowerCase();
		String[] searchObj = getSearchObj(search);
		
		//get the relevant Info from table
		String[][] relevant = new String[table.length-1][4];
		for(int i=0;i<relevant.length;i++) {
			relevant[i][0] = table[i][2];
			relevant[i][1] = table[i][3];
			relevant[i][2] = table[i][4];
			relevant[i][3] = table[i][9];
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
						resId[l] = relevant[i][0];
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
