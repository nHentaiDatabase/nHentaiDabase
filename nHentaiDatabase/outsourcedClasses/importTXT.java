package outsourcedClasses;

public class importTXT {

	public importTXT() {
		
	}
	
	public void splitData(String[] TextAreaData) {
		for (int i = 0; i < TextAreaData.length; i++) {
			String rawData = TextAreaData[i];
			String rawCode = "";
			String rawRating = "";
			boolean ratingTurn = false;
			char[] rawDataChar = rawData.toCharArray();
			for (int j = 0; j < rawDataChar.length; j++) {
				if (ratingTurn == true) {
					rawRating = rawRating + rawDataChar[j];
				}
				if (rawDataChar[j] == ' ') {
					ratingTurn = true;
				} else if (ratingTurn == false) {
					rawCode = rawCode + rawDataChar[j];
				}
			}
			if(!rawRating.equals(""))
				rawRating = rawRating.substring(0, rawRating.length() - 1);
		}
	}
}
