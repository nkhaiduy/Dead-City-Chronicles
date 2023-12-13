package com.group22.objects;

import com.group22.GamePanel;
import com.group22.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.mockito.Mockito.*;

class SuperObjectTest {

    @Mock
    private GamePanel mockGamePanel;
    @Mock
    private Player mockPlayer;
    @Mock
    private Graphics2D mockGraphics;

    private SuperObject superObject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockGamePanel = mock(GamePanel.class);
        mockPlayer = mock(Player.class);
        mockGraphics = mock(Graphics2D.class);

        superObject = new SuperObject();
        superObject.image = new BufferedImage(48, 48, BufferedImage.TYPE_INT_RGB);

        mockGamePanel.player = mockPlayer;
        mockGamePanel.tileSize = 48;
    }

    @Test
    void testDrawObjectOnScreen() {
        // Setup player and object positions
        superObject.worldX = 100;
        superObject.worldY = 100;
        mockPlayer.worldX = 50;
        mockPlayer.worldY = 50;
        mockPlayer.screenX = 400;
        mockPlayer.screenY = 300;

        // Call the draw method
        superObject.draw(mockGraphics, mockGamePanel);

        // Verify that the drawImage method is called with expected parameters
        verify(mockGraphics).drawImage(superObject.image, 450, 350, 48, 48, null);
    }
}
