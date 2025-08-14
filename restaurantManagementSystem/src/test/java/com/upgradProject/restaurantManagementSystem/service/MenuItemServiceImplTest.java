package com.upgradProject.restaurantManagementSystem.service;


import com.upgradProject.restaurantManagementSystem.entity.MenuItem;
import com.upgradProject.restaurantManagementSystem.exceptions.MenuItemNotAvailable;
import com.upgradProject.restaurantManagementSystem.repo.MenuItemRepository;
import com.upgradProject.restaurantManagementSystem.service.impls.MenuItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceImplTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuItemServiceImpl menuItemService;

    @BeforeEach
    void setUp() {
        reset(menuItemRepository);
    }

    @Test
    void getAllMenuItems_ReturnsListOfMenuItems() {
        MenuItem item1 = new MenuItem();
        MenuItem item2 = new MenuItem();
        when(menuItemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<MenuItem> result = menuItemService.getAllMenuItems();

        assertThat(result).containsExactly(item1, item2);
        verify(menuItemRepository).findAll();
    }

    @Test
    void getAllMenuItems_ReturnsEmptyListWhenNoMenuItems() {
        when(menuItemRepository.findAll()).thenReturn(Collections.emptyList());

        List<MenuItem> result = menuItemService.getAllMenuItems();

        assertThat(result).isEmpty();
        verify(menuItemRepository).findAll();
    }

    @Test
    void getMenuItemById_ReturnsMenuItemWhenFound() {
        MenuItem item = new MenuItem();
        item.setId(10);
        when(menuItemRepository.findById(10)).thenReturn(Optional.of(item));

        MenuItem result = menuItemService.getMenuItemById(10);

        assertThat(result).isEqualTo(item);
        verify(menuItemRepository).findById(10);
    }

    @Test
    void getMenuItemById_ThrowsMenuItemNotAvailableWhenNotFound() {
        when(menuItemRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> menuItemService.getMenuItemById(99))
                .isInstanceOf(MenuItemNotAvailable.class)
                .hasMessageContaining("MenuItem is not available id : 99");
        verify(menuItemRepository).findById(99);
    }
}
