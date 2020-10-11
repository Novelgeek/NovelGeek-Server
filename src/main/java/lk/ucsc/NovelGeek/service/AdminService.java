package lk.ucsc.NovelGeek.service;

import lk.ucsc.NovelGeek.dto.GenreStats;
import lk.ucsc.NovelGeek.model.Review;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.model.book.RecentlyViewed;
import lk.ucsc.NovelGeek.model.request.UserSignInModel;
import lk.ucsc.NovelGeek.model.request.UserSignUpModel;
import lk.ucsc.NovelGeek.model.response.AuthResponse;
import lk.ucsc.NovelGeek.model.response.BasicStat;
import lk.ucsc.NovelGeek.model.response.UserDetailsResponse;
import lk.ucsc.NovelGeek.repository.AuthRepository;
import lk.ucsc.NovelGeek.repository.ReviewRepository;
import lk.ucsc.NovelGeek.repository.book.RecentlyViewedRepository;
import lk.ucsc.NovelGeek.util.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthRepository authRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RecentlyViewedRepository recentlyViewedRepository;

    public Users getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users currentUser = authRepository.findByEmail(auth.getName());
        return currentUser;
    }

    public Users createAdmin(UserSignUpModel userDto) {
        if(authRepository.findByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("Email already exists, Please try a different email");
        }

        Users users = new Users();

        BeanUtils.copyProperties(userDto, users);
        users.setUsername(userDto.getUsername().toLowerCase());
        users.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        users.setRole("ADMIN");
        users.setProvider("local");
        Users storedUsers = authRepository.save(users);

        return storedUsers;
    }

    public AuthResponse login(UserSignInModel loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Users user = authRepository.findByEmail(loginRequest.getEmail());
            String token = jwtTokenUtil.generateToken(user);
            return new AuthResponse(token, loginRequest.getEmail());
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password", e);
        }
    }


    public Object getAllAdmins() {
        return authRepository.findByRole("ADMIN");
    }

    public ResponseEntity<GenreStats> getGenreStats(){
        List<RecentlyViewed> list=recentlyViewedRepository.findAll();
        HashMap<String,Integer> stats=new HashMap<>();
        GenreStats genreStats=new GenreStats();

        for (RecentlyViewed book : list) {
            String word=book.getGenre();


            if(word!=null){
                String arr[] = word.split(" ");
                String genre;
                if(arr[2]!=null && !arr[2].equals("/")){
                    genre=arr[2];
                }else{
                    genre = arr[0];
                }

                if(stats.containsKey(genre)){
                    stats.put(genre, stats.get(genre) + 1);
                }else{
                    stats.put(genre, 1);
                }
            }

        }
        Set<String> setOfCountries = stats.keySet();

        for(String key : setOfCountries) {
            genreStats.categories.add(key);
            genreStats.count.add(stats.get(key));
            System.out.println( key + "\t"+ stats.get(key));
        }

        return new ResponseEntity<GenreStats>(genreStats, HttpStatus.OK);
    }

    public List<Integer> getUserStats() {
        List<Review> list= (List<Review>) reviewRepository.findAll();
        HashMap<String, Integer> stats = new HashMap<>();
        stats.put("January", 0);
        stats.put("February", 0);
        stats.put("March", 0);
        stats.put("April", 0);
        stats.put("May", 0);
        stats.put("June", 0);
        stats.put("July", 0);
        stats.put("August", 0);
        stats.put("September", 0);
        stats.put("October", 0);
        stats.put("November", 0);
        stats.put("December", 0);

        for (Review review : list) {
            Date date=review.getTimestamp();
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM");

            String month = sdf.format(date);
            if(stats.containsKey(month)){
                stats.put(month, stats.get(month) + 1);
            }else{
                System.out.println("Doesn't contain"+ month);
            }
        }
        Set<String> setOfCountries = stats.keySet();
        List<Integer> realStat = new ArrayList<>();
        for(String key : setOfCountries) {
            realStat.add(stats.get(key));
            System.out.println( key + "\t"+ stats.get(key));
        }
        return realStat;
    }


    public void updateAdmin(UserDetailsResponse userDetailsResponse) {
        Users admin = this.getCurrentUser();
        admin.setUsername(userDetailsResponse.getUsername());
        authRepository.save(admin);
    }


}
