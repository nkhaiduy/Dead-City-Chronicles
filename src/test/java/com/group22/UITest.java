package com.group22;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.group22.entities.Player;

import java.awt.Font;

public class UITest {

    @Mock
    private GamePanel mockGamePanel;
    private UI uiTest;
    private Graphics2D mockGraphics;

    @BeforeEach
    public void setUp() {
        mockGamePanel = Mockito.mock(GamePanel.class);
        mockGraphics = Mockito.mock(Graphics2D.class);
        when(mockGamePanel.getGraphics()).thenReturn(mockGraphics);

        mockGamePanel.tileSize = 32;
        uiTest = new UI(mockGamePanel);
    }

    @Test
    public void testShowMessage() {
        assertFalse(uiTest.messageOn);

        uiTest.showMessage("Test Message");

        assertTrue(uiTest.messageOn);
        assertEquals("Test Message", uiTest.message);
    }

    @Test
    public void testDrawTitleScreen(){
         Font defaultFont = new Font("Arial", Font.PLAIN, 12);
        when(mockGraphics.getFont()).thenReturn(defaultFont);
        uiTest.draw(mockGraphics);
        verify(mockGraphics, times(5)).drawImage(
            any(BufferedImage.class),
            anyInt(),
            anyInt(),
            any()
         );
    }


    

}
