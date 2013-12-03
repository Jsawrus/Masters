package com.ftbmasters.recipes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ExpandingSnowBall {
	public ItemStack snowBall;
	public ItemMeta snowBallMeta;
	public ShapedRecipe recipe;

	public ExpandingSnowBall() {
		this.makeExpandingSnowball();
		this.addRecipe();
	}

	private void makeExpandingSnowball() {
		snowBall = new ItemStack(Material.SNOW_BALL, 1);
		snowBallMeta = snowBall.getItemMeta();
		snowBallMeta.setDisplayName(ChatColor.AQUA + "Super Snow Ball!");
		snowBallMeta.setLore(Arrays.asList(
				ChatColor.GREEN + "The ancient " + ChatColor.LIGHT_PURPLE + "SnowMan" + ChatColor.GOLD + " Mario",
				ChatColor.GREEN + "Approves this craft!"));
		snowBall.setItemMeta(snowBallMeta);
	}

	private void addRecipe() {
		recipe = new ShapedRecipe(this.snowBall);
		recipe.shape(new String[] { " S ", "SSS", " S " });
		recipe.setIngredient('S', Material.SNOW_BALL);

		Bukkit.addRecipe(recipe);
	}
}
