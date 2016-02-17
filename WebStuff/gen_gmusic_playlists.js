// Generates m3u playlists from Google Music pages
// Just run it in the JS console in chrome.

var download = function(name, content) {
  var element = document.createElement('a');
  element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(content));
  element.setAttribute('download', name);
  element.style.display = 'none';
  document.body.appendChild(element);
  element.click();
  document.body.removeChild(element);
};
var name = document.querySelector(".playlist-view .info .title").textContent;
if(window.madetall != "true") {
  document.querySelector("body.material").style.height = "99999px";
  window.madetall = "true";
  alert("Press okay to download: " + name);
}
window.setTimeout(function() {
	var rows = document.querySelectorAll("table.song-table tr.song-row");
	var m3u = "# Playlist: " + name + "\n";
	m3u += "\"Title\",\"Artist\",\"Album\"\n";
	for(var i = 0;i < rows.length;i++) {
		var row = rows[i];
		var title = row.querySelector("td[data-col=title] .column-content").textContent;
		var artist = row.querySelector("td[data-col=artist] .column-content").textContent;
		var album = row.querySelector("td[data-col=album] .column-content").textContent;
		var entry = "\"" + title + "\",\"" + artist + "\",\"" + album + "\"\n";
		m3u += entry;
	}
	download(name, m3u);
}, 500);
