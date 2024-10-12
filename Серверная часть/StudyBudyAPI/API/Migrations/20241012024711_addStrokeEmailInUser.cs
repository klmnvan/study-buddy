using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

#pragma warning disable CA1814 // Prefer jagged arrays over multidimensional

namespace StudyBudyAPI.Migrations
{
    /// <inheritdoc />
    public partial class addStrokeEmailInUser : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DeleteData(
                table: "AspNetRoles",
                keyColumn: "Id",
                keyValue: new Guid("4eb90fea-daef-4c03-8531-986d3abed3b6"));

            migrationBuilder.DeleteData(
                table: "AspNetRoles",
                keyColumn: "Id",
                keyValue: new Guid("c6bddac5-d33a-40e3-8e5a-a73d95267850"));

            migrationBuilder.AddColumn<string>(
                name: "Email",
                table: "Users",
                type: "text",
                nullable: false,
                defaultValue: "");

            migrationBuilder.InsertData(
                table: "AspNetRoles",
                columns: new[] { "Id", "ConcurrencyStamp", "Name", "NormalizedName" },
                values: new object[,]
                {
                    { new Guid("6f2b6aab-f631-4a1d-8085-c812e4c3ad81"), null, "Admin", "ADMIN" },
                    { new Guid("f8c4b8e8-4f84-43a2-a01d-63daee39472f"), null, "User", "USER" }
                });
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DeleteData(
                table: "AspNetRoles",
                keyColumn: "Id",
                keyValue: new Guid("6f2b6aab-f631-4a1d-8085-c812e4c3ad81"));

            migrationBuilder.DeleteData(
                table: "AspNetRoles",
                keyColumn: "Id",
                keyValue: new Guid("f8c4b8e8-4f84-43a2-a01d-63daee39472f"));

            migrationBuilder.DropColumn(
                name: "Email",
                table: "Users");

            migrationBuilder.InsertData(
                table: "AspNetRoles",
                columns: new[] { "Id", "ConcurrencyStamp", "Name", "NormalizedName" },
                values: new object[,]
                {
                    { new Guid("4eb90fea-daef-4c03-8531-986d3abed3b6"), null, "User", "USER" },
                    { new Guid("c6bddac5-d33a-40e3-8e5a-a73d95267850"), null, "Admin", "ADMIN" }
                });
        }
    }
}
