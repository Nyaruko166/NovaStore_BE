USE [master]
GO
/****** Object:  Database [Nova_Store_DB]    Script Date: 15/11/2023 16:24 PM ******/
CREATE DATABASE [Nova_Store_DB]
GO
USE [Nova_Store_DB]
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Nova_Store_DB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Nova_Store_DB] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ANSI_NULLS OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ANSI_PADDING OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ARITHABORT OFF
GO
ALTER DATABASE [Nova_Store_DB] SET AUTO_CLOSE ON
GO
ALTER DATABASE [Nova_Store_DB] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [Nova_Store_DB] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [Nova_Store_DB] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [Nova_Store_DB] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [Nova_Store_DB] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [Nova_Store_DB] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [Nova_Store_DB] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [Nova_Store_DB] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [Nova_Store_DB] SET  ENABLE_BROKER
GO
ALTER DATABASE [Nova_Store_DB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [Nova_Store_DB] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [Nova_Store_DB] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [Nova_Store_DB] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [Nova_Store_DB] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [Nova_Store_DB] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [Nova_Store_DB] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [Nova_Store_DB] SET RECOVERY SIMPLE
GO
ALTER DATABASE [Nova_Store_DB] SET  MULTI_USER
GO
ALTER DATABASE [Nova_Store_DB] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [Nova_Store_DB] SET DB_CHAINING OFF
GO
ALTER DATABASE [Nova_Store_DB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF )
GO
ALTER DATABASE [Nova_Store_DB] SET TARGET_RECOVERY_TIME = 60 SECONDS
GO
ALTER DATABASE [Nova_Store_DB] SET DELAYED_DURABILITY = DISABLED
GO
ALTER DATABASE [Nova_Store_DB] SET ACCELERATED_DATABASE_RECOVERY = OFF
GO
ALTER DATABASE [Nova_Store_DB] SET QUERY_STORE = ON
GO
ALTER DATABASE [Nova_Store_DB] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [Nova_Store_DB]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 10/10/2023 9:34:05 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [Email] [varchar](50) NULL,
    [PhoneNumber] [varchar](15) NULL,
    [Password] [varchar](255) NULL,
    [Birthday] [datetime] NULL,
    [Avatar] [varchar](255) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [RoleId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Address]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Address](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [SpecificAddress] [nvarchar](50) NULL,
    [Ward] [nvarchar](50) NULL,
    [District] [nvarchar](50) NULL,
    [City] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [AccountId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Bill]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Bill](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [CustomerName] [nvarchar](50) NULL,
    [Address] [nvarchar](100) NULL,
    [PhoneNumber] [nvarchar](15) NULL,
    [Note] [nvarchar](255) NULL,
    [OrderDate] [datetime] NULL,
    [PaymentDate] [datetime] NULL,
    [ConfirmationDate] [datetime] NULL,
    [ShippingDate] [datetime] NULL,
    [ReceivedDate] [datetime] NULL,
    [CompletionDate] [datetime] NULL,
    [ShippingFee] [money] NULL,
    [Price] [money] NULL,
    [DiscountAmount] [money] NULL,
    [TotalPrice] [money] NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [AccountId] [int] NULL,
    [CustomerId] [int] NULL,
    [VoucherId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[BillDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[BillDetail](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Price] [money] NULL,
    [Quantity] [int] NULL,
    [Status] [int] NULL,
    [BillId] [int] NULL,
    [ProductDetailId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[BillHistory]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[BillHistory](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [Describe] [nvarchar](255) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [BillId] [int] NULL,
    [AccountId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Brand]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Brand](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Cart]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Cart](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [TotalPrice] [money] NULL,
    [TotalItems] [int] NULL,
    [AccountId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[CartDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[CartDetail](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Price] [money] NULL,
    [Quantity] [int] NULL,
    [CartId] [int] NULL,
    [ProductDetailId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Category]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Category](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Color]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Color](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Form]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Form](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Image]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Image](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [varchar](255) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [ProductId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Material]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Material](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[PaymentMethod]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[PaymentMethod](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [Money] [money] NULL,
    [Description] [nvarchar](50) NULL,
    [Status] [int] NULL,
    [BillId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Product]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Product](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Code] [varchar](50) UNIQUE,
    [Name] [nvarchar](50) NULL,
    [Description] [nvarchar](255) NULL,
    [Price] [money] NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [BrandId] [int] NULL,
    [CategoryId] [int] NULL,
    [FormId] [int] NULL,
    [MaterialId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[ProductDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[ProductDetail](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Code] [varchar](50) UNIQUE,
    [Quantity] [int] NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [ProductId] [int] NULL,
    [SizeId] [int] NULL,
    [ColorId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Promotion]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Promotion](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Code] [varchar](255) NULL,
    [Name] [nvarchar](50) NULL,
    [Value] [money] NULL,
    [StartDate] [datetime] NULL,
    [EndDate] [datetime] NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[PromotionDetail]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[PromotionDetail](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    [ProductId] [int] NULL,
    [PromotionId] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Role]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Role](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Size]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Size](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Name] [nvarchar](50) NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Table [dbo].[Voucher]    Script Date: 10/10/2023 9:34:05 PM ******/
    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[Voucher](
    [Id] [int] IDENTITY(1,1) NOT NULL,
    [Code] [varchar](50) NULL,
    [Name] [nvarchar](50) NULL,
    [Value] [money] NULL,
    [Quantity] [int] NULL,
    [MinimumPrice] [money] NULL,
    [MaximumDiscount] [money] NULL,
    [StartDate] [datetime] NULL,
    [EndDate] [datetime] NULL,
    [CreateDate] [datetime] NULL,
    [UpdateDate] [datetime] NULL,
    [Status] [int] NULL,
    PRIMARY KEY CLUSTERED
(
[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
ALTER TABLE [dbo].[Account]  WITH CHECK ADD  CONSTRAINT [FKi9xmahyh65di7x2wn5fvt8lv3] FOREIGN KEY([RoleId])
    REFERENCES [dbo].[Role] ([Id])
    GO
ALTER TABLE [dbo].[Account] CHECK CONSTRAINT [FKi9xmahyh65di7x2wn5fvt8lv3]
    GO
ALTER TABLE [dbo].[Address]  WITH CHECK ADD  CONSTRAINT [FK3haj5uqn2j6ar58mcglooa5bp] FOREIGN KEY([AccountId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[Address] CHECK CONSTRAINT [FK3haj5uqn2j6ar58mcglooa5bp]
    GO
ALTER TABLE [dbo].[Bill]  WITH CHECK ADD  CONSTRAINT [FK5mrre5s0gacpqu6737kfocwkl] FOREIGN KEY([CustomerId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[Bill] CHECK CONSTRAINT [FK5mrre5s0gacpqu6737kfocwkl]
    GO
ALTER TABLE [dbo].[Bill]  WITH CHECK ADD  CONSTRAINT [FKdlpt4y8nuurj83nht236onfi6] FOREIGN KEY([AccountId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[Bill] CHECK CONSTRAINT [FKdlpt4y8nuurj83nht236onfi6]
    GO
ALTER TABLE [dbo].[Bill]  WITH CHECK ADD  CONSTRAINT [FK2hf3g6padqdy15tccpshmpxob] FOREIGN KEY([VoucherId])
    REFERENCES [dbo].[Voucher] ([Id])
    GO
ALTER TABLE [dbo].[Bill] CHECK CONSTRAINT [FK2hf3g6padqdy15tccpshmpxob]
    GO
ALTER TABLE [dbo].[BillDetail]  WITH CHECK ADD  CONSTRAINT [FK8sw1tfhht3q5xtdsyoe0r7jfd] FOREIGN KEY([BillId])
    REFERENCES [dbo].[Bill] ([Id])
    GO
ALTER TABLE [dbo].[BillDetail] CHECK CONSTRAINT [FK8sw1tfhht3q5xtdsyoe0r7jfd]
    GO
ALTER TABLE [dbo].[BillDetail]  WITH CHECK ADD  CONSTRAINT [FKnt7lacod5l24jdnfgxfydqiu2] FOREIGN KEY([ProductDetailId])
    REFERENCES [dbo].[ProductDetail] ([Id])
    GO
ALTER TABLE [dbo].[BillDetail] CHECK CONSTRAINT [FKnt7lacod5l24jdnfgxfydqiu2]
    GO
ALTER TABLE [dbo].[BillHistory]  WITH CHECK ADD  CONSTRAINT [FKdwtgkov1ar5uki43w7okh9xqi] FOREIGN KEY([BillId])
    REFERENCES [dbo].[Bill] ([Id])
    GO
ALTER TABLE [dbo].[BillHistory] CHECK CONSTRAINT [FKdwtgkov1ar5uki43w7okh9xqi]
    GO
ALTER TABLE [dbo].[BillHistory]  WITH CHECK ADD  CONSTRAINT [FKowiek2xkcm34kzm23jlzms92j] FOREIGN KEY([AccountId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[BillHistory] CHECK CONSTRAINT [FKowiek2xkcm34kzm23jlzms92j]
    GO
ALTER TABLE [dbo].[Cart]  WITH CHECK ADD  CONSTRAINT [FK1w1km3ww10t0maawf2cymyx5i] FOREIGN KEY([AccountId])
    REFERENCES [dbo].[Account] ([Id])
    GO
ALTER TABLE [dbo].[Cart] CHECK CONSTRAINT [FK1w1km3ww10t0maawf2cymyx5i]
    GO
ALTER TABLE [dbo].[CartDetail]  WITH CHECK ADD  CONSTRAINT [FK6unelr9lsy26gw9da5tuuxcsh] FOREIGN KEY([CartId])
    REFERENCES [dbo].[Cart] ([Id])
    GO
ALTER TABLE [dbo].[CartDetail] CHECK CONSTRAINT [FK6unelr9lsy26gw9da5tuuxcsh]
    GO
ALTER TABLE [dbo].[CartDetail]  WITH CHECK ADD  CONSTRAINT [FKepuvpwbykpahqt0gagvvdqoyn] FOREIGN KEY([ProductDetailId])
    REFERENCES [dbo].[ProductDetail] ([Id])
    GO
ALTER TABLE [dbo].[CartDetail] CHECK CONSTRAINT [FKepuvpwbykpahqt0gagvvdqoyn]
    GO
ALTER TABLE [dbo].[Image]  WITH CHECK ADD  CONSTRAINT [FKdtaisglfgjjj5j1a7g3fcev7c] FOREIGN KEY([ProductId])
    REFERENCES [dbo].[Product] ([Id])
    GO
ALTER TABLE [dbo].[Image] CHECK CONSTRAINT [FKdtaisglfgjjj5j1a7g3fcev7c]
    GO
ALTER TABLE [dbo].[PaymentMethod]  WITH CHECK ADD  CONSTRAINT [FKjity6x6p1194mtoli4abb3jgc] FOREIGN KEY([BillId])
    REFERENCES [dbo].[Bill] ([Id])
    GO
ALTER TABLE [dbo].[PaymentMethod] CHECK CONSTRAINT [FKjity6x6p1194mtoli4abb3jgc]
    GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK4cx1ir1xlnytlte2quullny9m] FOREIGN KEY([FormId])
    REFERENCES [dbo].[Form] ([Id])
    GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK4cx1ir1xlnytlte2quullny9m]
    GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FK6pnobu31k3yhhmk45s97imkui] FOREIGN KEY([CategoryId])
    REFERENCES [dbo].[Category] ([Id])
    GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FK6pnobu31k3yhhmk45s97imkui]
    GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FKfc8uiunvrolmn3qa9ahrhmtrw] FOREIGN KEY([MaterialId])
    REFERENCES [dbo].[Material] ([Id])
    GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FKfc8uiunvrolmn3qa9ahrhmtrw]
    GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [FKnuw1iwpj73v904j79uc8qurgc] FOREIGN KEY([BrandId])
    REFERENCES [dbo].[Brand] ([Id])
    GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [FKnuw1iwpj73v904j79uc8qurgc]
    GO
ALTER TABLE [dbo].[ProductDetail]  WITH CHECK ADD  CONSTRAINT [FKik38y3bjry9u05majdn5u3egj] FOREIGN KEY([ProductId])
    REFERENCES [dbo].[Product] ([Id])
    GO
ALTER TABLE [dbo].[ProductDetail] CHECK CONSTRAINT [FKik38y3bjry9u05majdn5u3egj]
    GO
ALTER TABLE [dbo].[ProductDetail]  WITH CHECK ADD  CONSTRAINT [FK61xkqx42jtcc8we64hahp05pv] FOREIGN KEY([SizeId])
    REFERENCES [dbo].[Size] ([Id])
    GO
ALTER TABLE [dbo].[ProductDetail] CHECK CONSTRAINT [FK61xkqx42jtcc8we64hahp05pv]
    GO
ALTER TABLE [dbo].[ProductDetail]  WITH CHECK ADD  CONSTRAINT [FKntlsi9s4irkogtc9mbw03s90y] FOREIGN KEY([ColorId])
    REFERENCES [dbo].[Color] ([Id])
    GO
ALTER TABLE [dbo].[ProductDetail] CHECK CONSTRAINT [FKntlsi9s4irkogtc9mbw03s90y]
    GO
ALTER TABLE [dbo].[PromotionDetail]  WITH CHECK ADD  CONSTRAINT [FKa83ktjk5axkasy5c9v2s1ukig] FOREIGN KEY([ProductId])
    REFERENCES [dbo].[Product] ([Id])
    GO
ALTER TABLE [dbo].[PromotionDetail] CHECK CONSTRAINT [FKa83ktjk5axkasy5c9v2s1ukig]
    GO
ALTER TABLE [dbo].[PromotionDetail]  WITH CHECK ADD  CONSTRAINT [FKos6ftbatvw4mk81km7xpkds5i] FOREIGN KEY([PromotionId])
    REFERENCES [dbo].[Promotion] ([Id])
    GO
ALTER TABLE [dbo].[PromotionDetail] CHECK CONSTRAINT [FKos6ftbatvw4mk81km7xpkds5i]
    GO
    USE [master]
    GO
ALTER DATABASE [Nova_Store_DB] SET  READ_WRITE
GO

USE Nova_Store_DB

SELECT * FROM Brand
    INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES (N'Reebok', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES (N'Asics', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES (N'Brooks', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES (N'Mizuno', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES (N'New balance', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES (N'Puma', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES (N'Nike', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES (N'Skechers', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES (N'Under armour', GETDATE(), GETDATE(), 1)
INSERT INTO Brand (name, CreateDate, UpdateDate, Status)
VALUES (N'Adidas', GETDATE(), GETDATE(), 1)


SELECT * FROM Category
    INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES (N'Áo thun', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES (N'Áo khoác', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES (N'Áo ngắn tay', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES (N'Áo Polo', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES (N'Áo dài tay', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES (N'Quần bó', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES (N'Quần cạp cao', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES (N'Quần đùi', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES (N'Quần dài', GETDATE(), GETDATE(), 1)
INSERT INTO Category (name, CreateDate, UpdateDate, Status)
VALUES (N'Quần legging', GETDATE(), GETDATE(), 1)

SELECT * FROM Material
    INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES (N'Spandex', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES (N'Cotton', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES (N'Mesh', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES (N'Nylon', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES (N'Viscose', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES (N'Microfiber', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES (N'Linen', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES (N'Syntheic', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES (N'Polyester', GETDATE(), GETDATE(), 1)
INSERT INTO Material (name, CreateDate, UpdateDate, Status)
VALUES (N'Fleece', GETDATE(), GETDATE(), 1)


SELECT * FROM Form
    INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES (N'Bó', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES (N'Rộng', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES (N'Tank top', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES (N'Jogger', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES (N'Dài', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES (N'Ngắn', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES (N'Dày', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES (N'Mỏng', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES (N'Lưới', GETDATE(), GETDATE(), 1)
INSERT INTO Form (name, CreateDate, UpdateDate, Status)
VALUES (N'Thiếu vải', GETDATE(), GETDATE(), 1)


SELECT * FROM Size
    INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES (N'28', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES (N'29', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES (N'30', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES (N'31', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES (N'32', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES (N'XXL', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES (N'XL', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES (N'L', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES (N'S', GETDATE(), GETDATE(), 1)
INSERT INTO Size (name, CreateDate, UpdateDate, Status)
VALUES (N'M', GETDATE(), GETDATE(), 1)


SELECT * FROM Color
    INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES (N'Đen', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES (N'Đỏ', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES (N'Xanh dương', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES (N'Xanh lá', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES (N'Vàng', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES (N'Trắng', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES (N'Xám', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES (N'Ghi', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES (N'Nâu', GETDATE(), GETDATE(), 1)
INSERT INTO Color (name, CreateDate, UpdateDate, Status)
VALUES (N'Cam', GETDATE(), GETDATE(), 1)


SELECT * FROM Product
    INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0001', N'Áo thun tay ngắn', N'vip pro', 123000, GETDATE(), GETDATE(), 1, 1, 1, 1, 1);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0002', N'Áo khoác Flannel tay dài kháo kéo', N'Chiến thần', 234000, GETDATE(), GETDATE(), 1, 2, 2, 2, 2);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0003', N'Áo khoác bomber Nylon', N'quá là áo', 345000, GETDATE(), GETDATE(), 1, 3, 3, 3, 3);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0004', N'Áo len Polo', N'vip plus', 222000, GETDATE(), GETDATE(), 1, 4, 4, 4, 4);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0005', N'Áo Gile len', N'trên cả tuyệt vời', 33000, GETDATE(), GETDATE(), 1, 5, 5, 5, 5);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0006', N'Áo Hoodie unisex', N'áo này đẹp lắm', 44000, GETDATE(), GETDATE(), 1, 6, 6, 6, 6);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0007', N'Áo Polo vải gân', N'mua đi đừng ngại', 55000, GETDATE(), GETDATE(), 1, 7, 7, 7, 7);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0008', N'Áo dài tay', N'mua đi nghèo lắm rồi', 678000, GETDATE(), GETDATE(), 1, 8, 8, 8, 8);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0009', N'Áo Superman', N'mua đi đừng ngại', 789000, GETDATE(), GETDATE(), 1, 9, 9, 9, 9);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0010', N'Áo Batman', N'mua đi nghèo lắm rồi',367000, GETDATE(), GETDATE(), 1, 10, 10, 10, 10);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0011', N'Áo Hulk', N'mua đi nghèo lắm rồi', 112000, GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0012', N'Áo Hulk', N'mua đi nghèo lắm rồi', 493000, GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0013', N'Áo Hulk', N'mua đi nghèo lắm rồi', 236000, GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0014', N'Áo Hulk', N'mua đi nghèo lắm rồi', 567000, GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0015', N'Áo Hulk', N'mua đi nghèo lắm rồi', 777000, GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0016', N'Áo Hulk', N'mua đi nghèo lắm rồi', 900000, GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0017', N'Áo Hulk', N'mua đi nghèo lắm rồi', 121000, GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0018', N'Áo Hulk', N'mua đi nghèo lắm rồi', 351000, GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0019', N'Áo Hulk', N'mua đi nghèo lắm rồi', 684000, GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0020', N'Áo Hulk', N'mua đi nghèo lắm rồi', 686000, GETDATE(), GETDATE(), 1, 1, 2,3, 4);
INSERT INTO Product (code, name, Description, Price, CreateDate, UpdateDate, Status, BrandId, CategoryId, FormId, MaterialId)
VALUES ('SP0021', N'Áo Superman', N'mua đi nghèo lắm rồi', 686000, GETDATE(), GETDATE(), 1, 1, 2,3, 4);
USE Nova_Store_DB
SELECT * FROM Product

SELECT * FROM ProductDetail
    INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0001', 12, GETDATE(), GETDATE(), 1, 1, 1, 1);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0002', 22, GETDATE(), GETDATE(), 1, 2, 2, 3);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0003', 30, GETDATE(), GETDATE(), 1, 3, 1, 2);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0004', 40, GETDATE(), GETDATE(), 1, 4, 2, 3);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0005', 31, GETDATE(), GETDATE(), 1, 5, 3, 4);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0006', 12, GETDATE(), GETDATE(), 1, 6, 5, 6);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0007', 11, GETDATE(), GETDATE(), 1, 7, 7, 8);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0008', 60, GETDATE(), GETDATE(), 1, 8, 9, 10);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0009', 27, GETDATE(), GETDATE(), 1, 9, 2, 1);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0010', 38, GETDATE(), GETDATE(), 1, 10, 4, 3);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0011', 36, GETDATE(), GETDATE(), 1, 11, 4, 9);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0012', 18, GETDATE(), GETDATE(), 1, 12, 2, 7);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0013', 24, GETDATE(), GETDATE(), 1, 13, 3, 8);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0014', 44, GETDATE(), GETDATE(), 1, 14, 10, 4);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0015', 55, GETDATE(), GETDATE(), 1, 15, 6, 1);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0016', 46, GETDATE(), GETDATE(), 1, 16, 3, 5);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0017', 71, GETDATE(), GETDATE(), 1, 17, 1, 4);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0018', 28, GETDATE(), GETDATE(), 1, 18, 9, 3);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0019', 23, GETDATE(), GETDATE(), 1, 19, 10, 2);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0020', 26, GETDATE(), GETDATE(), 1, 20, 2, 4);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0021', 2, GETDATE(), GETDATE(), 1, 21, 5, 8);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0022', 34, GETDATE(), GETDATE(), 1, 1, 2, 1);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0023', 51, GETDATE(), GETDATE(), 1, 2, 3, 3);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0024', 61, GETDATE(), GETDATE(), 1, 3, 2, 8);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0025', 73, GETDATE(), GETDATE(), 1, 4, 3, 5);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0026', 82, GETDATE(), GETDATE(), 1, 5, 2, 7);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0027', 23, GETDATE(), GETDATE(), 1, 6, 4, 1);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0028', 73, GETDATE(), GETDATE(), 1, 7, 2, 2);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0029', 28, GETDATE(), GETDATE(), 1, 8, 5, 1);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0030', 38, GETDATE(), GETDATE(), 1, 9, 3, 9);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0031', 128, GETDATE(), GETDATE(), 1, 10, 2, 1);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0032', 38, GETDATE(), GETDATE(), 1, 11, 2, 9);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0033', 282, GETDATE(), GETDATE(), 1, 12, 5, 7);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0034', 382, GETDATE(), GETDATE(), 1, 13, 5, 4);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0035', 311, GETDATE(), GETDATE(), 1, 14, 5, 5);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0036', 111, GETDATE(), GETDATE(), 1, 15, 9, 7);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0037', 112, GETDATE(), GETDATE(), 1, 16, 10, 10);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0038', 138, GETDATE(), GETDATE(), 1, 17, 5, 3);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0039', 222, GETDATE(), GETDATE(), 1, 18, 6, 6);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0040', 100, GETDATE(), GETDATE(), 1, 19, 2, 10);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0041', 99, GETDATE(), GETDATE(), 1, 20, 1, 1);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0042', 28, GETDATE(), GETDATE(), 1, 21, 2, 3);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0043', 12, GETDATE(), GETDATE(), 1, 1, 1, 4);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0044', 14, GETDATE(), GETDATE(), 1, 2, 2, 5);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0045', 223, GETDATE(), GETDATE(), 1, 3, 9, 1);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0046', 333, GETDATE(), GETDATE(), 1, 4, 8, 3);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0047', 492, GETDATE(), GETDATE(), 1, 5, 2, 1);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0048', 389, GETDATE(), GETDATE(), 1, 6, 2, 4);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0049', 123, GETDATE(), GETDATE(), 1, 7, 1, 2);
INSERT INTO ProductDetail (code, quantity, CreateDate, UpdateDate, Status, ProductId, SizeId, ColorId)
VALUES ('CT0050', 324, GETDATE(), GETDATE(), 1, 8, 2, 3);
