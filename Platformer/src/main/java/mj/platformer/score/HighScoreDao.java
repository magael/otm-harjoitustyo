/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mj.platformer.score;

/**
 *
 * @author Maguel
 */
public interface HighScoreDao {

    long getHighScore(int level);

    void setHighScore(long score, int level);

    void readHighScore(String filePath, int levelCount);

    void writeHighScore(String filePath, int levelCount);
    
}
