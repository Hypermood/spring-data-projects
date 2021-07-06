package course.springdata.gamestore.constants;

public class ConstructorTableVisualization {

    public static final String TABLE_VISUALIZATION =
            "RegisterUser|<email>|<password>|<confirmPassword>|<fullName>\n" +
                    "LoginUser|<email>|<password>\n" +
                    "Logout|<user>\n" +
                    "AddGame|<user>|<title>|<price>|<size>|<trailer>|<thubnailURL>|<description>|<releaseDate>\n" +
                    "EditGame|<user>|<id>|<values>\n" +
                    "DeleteGame|<user>|<id>\n" +
                    "AllGames|<user>\n" +
                    "DetailsGame|<user>|<gameTitle>\n" +
                    "OwnedGames|<user>\n" +
                    "AddItem|<gameTitle>\n" +
                    "RemoveItem|<gameTitle>\n" +
                    "BuyItem|<gameTitle>\n" +
                    "BuyAllItems";
}
