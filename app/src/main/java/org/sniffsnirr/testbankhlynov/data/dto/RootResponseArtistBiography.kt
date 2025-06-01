import com.google.gson.annotations.SerializedName

data class RootResponseArtistBiography (

	@SerializedName("artist") val artist : Artist
)

data class Artist (
	@SerializedName("name") val name : String,
	@SerializedName("mbid") val mbid : String,
	@SerializedName("url") val url : String,
	@SerializedName("image") val image : List<Image>,
	@SerializedName("streamable") val streamable : Int,
	@SerializedName("ontour") val ontour : Int,
	@SerializedName("stats") val stats : Stats,
	@SerializedName("similar") val similar : Similar,
	@SerializedName("tags") val tags : Tags,
	@SerializedName("bio") val bio : Bio
)

data class Image (
	@SerializedName("#text") val text : String,
	@SerializedName("size") val size : String
)

data class Stats (
	@SerializedName("listeners") val listeners : Int,
	@SerializedName("playcount") val playcount : Int
)

data class Similar (
	@SerializedName("artist") val artist : List<Artist>
)

data class Tags (
	@SerializedName("tag") val tag : List<Tag>
)
data class Tag (
	@SerializedName("name") val name : String,
	@SerializedName("url") val url : String
)

data class Bio (

	@SerializedName("links") val links : Links,
	@SerializedName("published") val published : String,
	@SerializedName("summary") val summary : String,
	@SerializedName("content") val content : String
)

data class Links (
	@SerializedName("link") val link : Link
)

data class Link (

	@SerializedName("#text") val text : String,
	@SerializedName("rel") val rel : String,
	@SerializedName("href") val href : String
)