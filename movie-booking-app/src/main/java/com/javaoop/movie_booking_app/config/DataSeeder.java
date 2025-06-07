package com.javaoop.movie_booking_app.config;

import com.javaoop.movie_booking_app.model.Movie;
import com.javaoop.movie_booking_app.model.RatingCategory;
import com.javaoop.movie_booking_app.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    private final MovieRepository movieRepository;

    public DataSeeder(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    private void seedMovies() {
        movieRepository.deleteAll();

        Movie movie = new Movie(
                "美國隊長：無畏新世界",
                "Captain America: Brave New World",
                "美國隊長.jpeg",
                "漫威電影宇宙的第35部電影，新一任美國隊長、同時也是獵鷹的前美隊得力助手山姆威爾森，扛起保衛美國的重責大任，也象徵嶄新時代正式開啟，在新世界新秩序的衝突升級下，他能否奮力扛起盾牌？",
                118,
                RatingCategory.保護級
        );
        movieRepository.save(movie);

        movie = new Movie(
                "機動戰士Gundam GquuuuuuX -Beginning-",
                "Mobile Suit Gundam GQuuuuuuX: Beginning",
                "機動戰士.jpeg",
                "由鶴卷和哉執導，庵野秀明與榎戶洋司負責故事腳本，日本票房冠軍，是由製作《新世紀福音戰士》的khara與《機動戰士》SUNRISE兩家公司聯手的全新企劃。",
                81,
                RatingCategory.普遍級
        );
        movieRepository.save(movie);

        movie = new Movie(
                "夜校女生",
                "The Uniform",
                "夜校女生.jpeg",
                "莊景燊執導，陳姸霏、項婕如、邱以太主演，攜手共譜「登大人」的成長痛。",
                109,
                RatingCategory.普遍級
        );
        movieRepository.save(movie);

        movie = new Movie(
                "史蒂芬金之猴子",
                "The Monkey",
                "史蒂芬金之猴子.jpeg",
                "改編自史蒂芬金1980年的同名短篇故事，溫子仁監製。《分歧者》席歐詹姆斯一人分飾兩角，對抗史上最兇殘詛咒！發條聲響起，離奇死亡事件即將展開！",
                98,
                RatingCategory.限制級
        );
        movieRepository.save(movie);

        movie = new Movie(
                "殺人預言",
                "Nocturnal",
                "殺人預言.jpeg",
                "《白頭山：半島浩劫》河正宇、《緊急迫降》金南佶、《諜影殺機》劉多仁主演， 一本暢銷小說竟預言了我弟的死！",
                99,
                RatingCategory.輔導級
        );
        movieRepository.save(movie);

        movie = new Movie(
                "窒息倒數",
                "Last Breath",
                "窒息倒數.jpeg",
                "改編真實海底災難事件，一段充滿驚心動魄又扣人心弦的災難救援，先從屏住呼吸開始…",
                93,
                RatingCategory.普遍級
        );
        movieRepository.save(movie);

        movie = new Movie(
                "火線追緝令",
                "Seven",
                "火線追緝令.jpeg",
                "首開先例引經據典舖陳劇情，包括心理學家的推陳分析，警匪雙方鬥智的曲折過程一一呈現無遺。由大衛芬奇執導，布萊德彼特與摩根佛里曼兩大巨星聯手，凱文史貝西則扮演瘋狂殺手。",
                126,
                RatingCategory.保護級
        );
        movieRepository.save(movie);

        movie = new Movie(
                "粗獷派建築師",
                "The Brutalist",
                "粗獷派建築師.jpeg",
                "2025奧斯卡10項大獎提名，2024威尼斯影展最佳導演獎，獲得2025金球獎最佳影片、最佳導演、最佳男主角三項大獎，《戰地琴人》安卓亞布洛迪、《愛的萬物論》費莉絲蒂瓊斯主演。",
                215,
                RatingCategory.保護級
        );
        movieRepository.save(movie);
    }

    @Override
    public void run(String... args) throws Exception {
        seedMovies();
    }
}
